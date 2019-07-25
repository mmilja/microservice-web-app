#! /bin/bash

source $(git rev-parse --show-toplevel)/build/build_common.sh

trap clean_up EXIT

MOUNT_HOST_VOLUME=0

TARGET_BUILD=0
TARGET_PUSH=0

MICROSERVICE_VERSION=''
REPOSITORY_ROOT_DIR=''
SOURCE_PATH=''
MAVEN_CONTAINER_NAME=''
TARGET_NAME=''
YCSB_UTIL_VERSION_TAG=''

function clean_up() {

    echo "Clean up "
    container_kill ${MAVEN_CONTAINER_NAME}
}

function set_integration_specific_variables() {

    MICROSERVICE_VERSION=$(get_service_version)

    MAVEN_CONTAINER_NAME="maven_container"

    BACK_END_IMAGE="${DOCKER_REPO}:${BACK_END}-${MICROSERVICE_VERSION}"
    FRONT_END_IMAGE="${DOCKER_REPO}:${FRONT_END}-${MICROSERVICE_VERSION}"
}

function generate_front_end_image() {
    local source_dir="/${SOURCE_PATH}/${FRONT_END}"
    local time_now="$(date -u +'%Y-%m-%dT%H:%M:%SZ')"

    echo "front-end image tag is ${FRONT_END_IMAGE}"

    local dockerfile_dir="${SOURCE_PATH}/${FRONT_END}"

	docker build -t ${FRONT_END_IMAGE} \
			 -f ${dockerfile_dir}/Dockerfile \
			 ${REPOSITORY_ROOT_DIR} \
             --build-arg PRODUCT_NAME="${SERVICE_NAME}" \
             --build-arg PRODUCT_BUILD_DATE="${time_now}" \
             --build-arg PRODUCT_VCS_REF="${commit_hash}" \
             --build-arg PRODUCT_VENDOR="${SERVICE_VENDOR}" \
             --build-arg PRODUCT_VERSION="${MICROSERVICE_VERSION}"
	check_result $? "Failed to build ${FRONT_END} image!"
}


function generate_back_end_image() {
    local source_dir="/${SOURCE_PATH}/${BACK_END}"
    local time_now="$(date -u +'%Y-%m-%dT%H:%M:%SZ')"

    echo "back-end image tag is ${BACK_END_IMAGE}"

    local run_args=" --rm -v ${REPOSITORY_ROOT_DIR}/pom.xml:/usr/src/app/pom.xml \
                    -v ${REPOSITORY_ROOT_DIR}/mvn-resources:/usr/src/app/mvn-resources \
                    -v ${SOURCE_PATH}:/usr/src/app/source \
                    -v ${HOME}/.m2/:/root/.m2 \
                    --cpus=2 --memory=4g \
                    ${JAVA_BUILDER_IMG} \
                    mvn --projects source/back-end clean install"
    docker run ${run_args}
    check_result $? "Failed to run maven install!"

    local dockerfile_dir="${SOURCE_PATH}/${BACK_END}"

	docker build -t ${BACK_END_IMAGE} \
			 -f ${dockerfile_dir}/Dockerfile \
			 ${REPOSITORY_ROOT_DIR} \
             --build-arg PRODUCT_NAME="${SERVICE_NAME}" \
             --build-arg PRODUCT_BUILD_DATE="${time_now}" \
             --build-arg PRODUCT_VCS_REF="${commit_hash}" \
             --build-arg PRODUCT_VENDOR="${SERVICE_VENDOR}" \
             --build-arg PRODUCT_VERSION="${MICROSERVICE_VERSION}"
	check_result $? "Failed to build ${BACK_END} image!"
}

function install_parent_pom() {

    docker exec ${MAVEN_CONTAINER_NAME} mvn clean install -f /usr/src/app/ -N
    check_result $? "Failed to install parent pom.xml"
    docker cp ${MAVEN_CONTAINER_NAME}:/usr/src/app/target ${REPOSITORY_ROOT_DIR}/
    check_result $? "Failed while copying target/ to repository"
}

function deploy_parent_pom() {

    docker exec ${MAVEN_CONTAINER_NAME} mvn clean deploy -f /usr/src/app/ -N
    check_result $? "Failed to deploy parent pom.xml to dev repo"
}

function maven_container_prepare() {
    echo "Preparing maven container"
    local container_name=$1
    local run_args=""
    if [[ ${MOUNT_HOST_VOLUME} -eq 1 ]]; then
        run_args="-v ${HOME}/.m2/:/root/.m2/"
    fi

    # start container
    docker run ${run_args} -d --rm --name="${container_name}" ${JAVA_BUILDER_IMG} /bin/sh -c 'while true; do sleep 1000; done'
    check_result $? "Failed to run container ${container_name}"

    # copy .git
    docker cp ${REPOSITORY_ROOT_DIR}/.git ${container_name}:/usr/src/app/
    check_result $? "Failed to copy .git into container"

    # copy parent pom
    docker cp ${REPOSITORY_ROOT_DIR}/${PARENT_POM_XML} ${container_name}:/usr/src/app/
    check_result $? "Failed to copy ${PARENT_POM_XML} into container"

    # copy source/
    docker cp ${REPOSITORY_ROOT_DIR}/${SOURCE_DIR} ${container_name}:/usr/src/app/
    check_result $? "Failed to copy source into container"
}

function usage() {

 cat <<EOF

Usage:
  $(basename $0) [options]

  This script is used to build and upload source images and maven artifacts.
  Options:
    --build <target_name> | -b <target_name> : Build image or install maven artifact.
    --push  <target_name> | -p <target_name> : Push image or deploy maven artifact to remote repository.

                                             Supported target names:
                                                         - back-end
                                                         - front-end
                                                         - all-images (all above images)
                                                         - parent-pom

    --mount-host-volume  | -v : Mounts maven host repository in the container to use cached artifacts.
                                Mounts as well host source code directory in the container.
                                When false source code is copied to generate an image and compile the binary
    --help  | -h : help

EOF
}

function parse_arguments() {

    PARSED_OPTIONS=$(getopt -n "$0" -o b:p:hlv  -l help,build:,push:,local,mount-host-volume -- "$@")
    OPTION_RET=$?
    eval set -- "${PARSED_OPTIONS}"
    if [[ ${OPTION_RET} -ne 0 ]]
    then
        usage
        exit 1
    fi

    while [[ $# -ge 1 ]]
    do
        case $1 in
            -b | --build )
                shift;
                TARGET_BUILD=1
                TARGET_NAME=$1
            ;;
            -p | --push )
                shift;
                TARGET_PUSH=1
                TARGET_NAME=$1
            ;;
            -v | --mount-host-volume ) MOUNT_HOST_VOLUME=1;;
            -h | --help ) usage;exit 0;;
            -- ) shift;break;;
            * ) "Unsupported parameter $1";exit 2;;
        esac
        shift
    done
}

function build() {

    SOURCE_PATH="${REPOSITORY_ROOT_DIR}/${SOURCE_DIR}"

    generate_pom  "${REPOSITORY_ROOT_DIR}"

    if [[ ${TARGET_NAME} = ${TARGET_PARENT_POM} ]]; then
        maven_container_prepare ${MAVEN_CONTAINER_NAME}
        install_parent_pom
    fi

    if [[ ${TARGET_NAME} = ${TARGET_BACK_END_IMAGE} ]]; then
        generate_back_end_image
    fi

    if [[ ${TARGET_NAME} = ${TARGET_FRONT_END_IMAGE} ]]; then
        generate_front_end_image
    fi

    if [[ ${TARGET_NAME} = ${TARGET_ALL_IMAGES} ]]; then
        generate_back_end_image
        generate_front_end_image
    fi
}

function push() {

    generate_pom "${REPOSITORY_ROOT_DIR}"

    if [[ ${TARGET_NAME} = ${TARGET_PARENT_POM} ]]; then
       maven_container_prepare ${MAVEN_CONTAINER_NAME}
       deploy_parent_pom
    fi

    if [[ ${TARGET_NAME} = ${TARGET_BACK_END_IMAGE} ]]; then
        push_container_image ${BACK_END_IMAGE}
    fi

    if [[ ${TARGET_NAME} = ${TARGET_FRONT_END_IMAGE} ]]; then
        push_container_image ${FRONT_END_IMAGE}
    fi

    if [[ ${TARGET_NAME} = ${TARGET_ALL_IMAGES} ]]; then
        push_container_image ${BACK_END_IMAGE}
        push_container_image ${FRONT_END_IMAGE}
    fi
}

function main() {

    REPOSITORY_ROOT_DIR=$(set_repo_root_dir)
    set_product_info $REPOSITORY_ROOT_DIR
    parse_arguments $@
    set_integration_specific_variables

    if [[ ${TARGET_BUILD} -eq 1 ]]; then
        build
    fi

    if [[ ${TARGET_PUSH} -eq 1 ]]; then
        push
    fi
}

main $@

