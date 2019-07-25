#!/bin/bash

source $(git rev-parse --show-toplevel)/build/build_common.sh

REPOSITORY_ROOT_DIR=''
BUILD_SELECTION=''
PUSH_SELECTION=''
MOUNT_HOST_VOLUME=''

FORCE=0
BUILD_OPTION=0
PUSH_OPTION=0
SKIP_UT=''
GENERATE_POM=0

function build_util_image() {

    echo "Building util-image: $1"
    ${REPOSITORY_ROOT_DIR}/${IMAGES}/build.sh -b ${1}
    check_result $? "Failed to build image: $1"
}

function build_util_images() {

    build_util_image ${OS_IMAGE_NAME}
    build_util_image ${JAVA_IMAGE_NAME}
    build_util_image ${REACT_IMAGE_NAME}
    build_util_image ${NGINX_IMAGE_NAME}
}

function push_util_image() {

    echo "Pushing util-image: $1"
    ${REPOSITORY_ROOT_DIR}/${IMAGES}/build.sh -p ${1}
    check_result $? "Failed to push image: $1"
}

# Function used to push util-images to ARM. Only newly created images (images impacted
# in the last commit) will be considered for push.
function push_util_images() {

    push_util_image ${OS_IMAGE_NAME}
    push_util_image ${JAVA_IMAGE_NAME}
    push_util_image ${REACT_IMAGE_NAME}
    push_util_image ${NGINX_IMAGE_NAME}
}

function usage() {
 cat <<EOF
  Usage:
  $(basename $0) [options]
  Main build script in charge of building and pushing specified targets. The default mode is to use "selective" build and push,
  by only considering targets impacted with the last commit.

  Options:
    -b <target>, --build <target>: Build image or install maven artifact.
    -p <target>, --push <target> : Push image or deploy maven artifact to remote repository.

                                Possible targets for build and push:
                                            - source             : source images.
                                            - chart              : helm chart.
                                            - images             : images.
                                            - parent-pom         : parent pom.xml.

    -v, --mount-host-volume: Mounts maven host repository in the container to use cached artifacts.
                             Mounts as well host source code directory in the container.
                             When false source code is copied to generate an image and compile the binary
    -g, --generate-pom:      Generate pom.xml files from templates.
    -f, --force: build the given target without selection (applies for source,chart and doc).
    -h,--help: Display help information
EOF
}

function parse_arguments() {
    PARSED_OPTIONS=$(getopt -n "$0" -o ghb:p:vlf -l generate-pom,help,build:,push:,use-production-tags,use-password-from-env,mount-host-volume,use-etk-proxy,local,skip-ut,force -- "$@")
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
                    BUILD_OPTION=1
                    BUILD_SELECTION="${BUILD_SELECTION} $1"
            ;;
            -p | --push )
                    shift;
                    PUSH_OPTION=1
                    PUSH_SELECTION="${PUSH_SELECTION} $1"
            ;;
            -v | --mount-host-volume ) MOUNT_HOST_VOLUME="-v";;
            -g | --generate-pom ) GENERATE_POM=1;;
            -f | --force ) FORCE=1;;
            -h | --help ) usage;exit 0;;
            -- ) shift;break;;
            * ) "Unsupported parameter $1";exit 2;;
        esac
        shift
    done
}

# This method will trigger the build of source images if the last commit
# had any impacts on those images.
function build_source() {

    echo "Building source images."
    ${REPOSITORY_ROOT_DIR}/${SOURCE_DIR}/build.sh -b ${TARGET_ALL_IMAGES} ${MOUNT_HOST_VOLUME}
    check_result $? "Failed to build source images."
}

function deploy_parent_pom() {

    echo "Deploying parent pom.xml"
    ${REPOSITORY_ROOT_DIR}/${SOURCE_DIR}/build.sh -p parent-pom ${MOUNT_HOST_VOLUME}
    check_result $? "Failed to deploy parent pom.xml"
}


# This method will package helm chart if the last commit had any impacts on the chart.
function package_helm_chart() {

    echo "Packaging helm chart."
    ${REPOSITORY_ROOT_DIR}/${CHARTS}/build.sh --package
    check_result $? "Failed to package helm chart."
}

function install_parent_pom() {

    echo "Installing parent pom.xml"
    ${REPOSITORY_ROOT_DIR}/${SOURCE_DIR}/build.sh -b ${TARGET_PARENT_POM} ${MOUNT_HOST_VOLUME}
    check_result $? "Failed to install parent pom.xml"
}

function build() {

    if [[ ${BUILD_SELECTION} =~ ${TARGET_PARENT_POM} ]]; then
        install_parent_pom
    fi

    if [[ ${BUILD_SELECTION} =~ ${TARGET_SOURCE} ]]; then
        build_source
    fi

    if [[ ${BUILD_SELECTION} =~ ${TARGET_CHART} ]]; then
        package_helm_chart
    fi

    if [[ ${BUILD_SELECTION} =~ ${TARGET_IMAGES} ]]; then
        build_util_images
    fi

}

function push_source() {

    echo "Pushing source images."
    ${REPOSITORY_ROOT_DIR}/${SOURCE_DIR}/build.sh -p ${TARGET_ALL_IMAGES}
    check_result $? "Failed to push source images."
}


function push_helm_chart() {

    echo "Pushing helm chart."
    ${REPOSITORY_ROOT_DIR}/${BENCHMARK_BASE}/${CHARTS}/build.sh --push
    check_result $? "Failed to push helm chart."
}

function push() {

    if [[ ${PUSH_SELECTION} =~ ${TARGET_PARENT_POM} ]]; then
        deploy_parent_pom
    fi

    if [[ ${PUSH_SELECTION} =~ ${TARGET_SOURCE} ]]; then
        push_source
    fi

    if [[ ${PUSH_SELECTION} =~ ${TARGET_CHART} ]]; then
        push_helm_chart
    fi

    if [[ ${PUSH_SELECTION} =~ ${TARGET_IMAGES} ]]; then
        push_util_images
    fi
}

function main() {
    REPOSITORY_ROOT_DIR=$(set_repo_root_dir)
    parse_arguments $@

    if [[ ${BUILD_OPTION} -eq 1 ]]; then
        build
    fi

    if [[ ${PUSH_OPTION} -eq 1 ]]; then
        push
    fi

    if [[ ${GENERATE_POM} -eq 1 ]]; then
        generate_pom ${USE_PRODUCTION_TAGS}
    fi
}

main $@
