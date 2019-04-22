#!/bin/bash

# This script holds constants and methods used by several build scripts.

# repository for test docker images. Used in local testing and jenkins test job.
# TODO create dockerhub repo
readonly DOCKER_REPO="mmilja/webapp"

readonly MICROSERVICE_NAME="web-app"

readonly JAVA_BUILDER_IMG="mmilja/webapp/java-base:0.0.1"

# maven arguments used if you want to skip the below mentioned lifecycle phases.
readonly SKIP_UT_ARGS="-Dmaven.test.skip=true -Dspotbugs.skip -Dpmd.skip -Dcheckstyle.skip"

#util image names
readonly OS_IMAGE_NAME="os-base"
readonly JAVA_IMAGE_NAME="java-base"

# image names
readonly BACK_END_IMAGE_NAME="back-end"
readonly IMAGE_VERSION_FILE="VERSION"

# directory names
readonly BACK_END='back-end'
readonly FRONT_END='front-end'
readonly CHARTS='charts'
readonly SOURCE_DIR='source'
readonly IMAGES='images'
readonly BASE_IMAGES='base-images'
readonly MAVEN_RESOURCES='mvn-resources'

# file names
readonly MICROSERVICE_VERSION_FILE="MICROSERVICE_VERSION"
readonly PARENT_POM_XML="pom.xml"
readonly POM_TEMPLATE="pom.xml.template"

# targets for build scripts. They are placed in common to make sure
# that we use the same name for a target in all build scripts.
readonly TARGET_PARENT_POM="parent-pom"
readonly TARGET_SOURCE="source"
readonly TARGET_CHART="chart"
readonly TARGET_VERSION="repository-version"
readonly TARGET_ALL_IMAGES="all-images"
readonly TARGET_BACK_END_IMAGE="back-end"
readonly TARGET_IMAGES="images"

# placeholders for maven dependency versions. In a pom.xml, if you find a
# placeholder as the version of a dependency, it means you want to use the
# newest possible version of that dependency. If the pom.xml does not use
# the placeholder but an exact version, that exact version will be used for
# that dependency.
readonly BACK_END_VERSION_TEMPLATE="%BACK_END_VERSION%"

# kill the container with the given name if it exists.
# arguments:
#  $1 - container name
function container_kill() {

    local container_name=$1
    if [[ ! -z ${container_name} && "$(docker ps -q -f name=${container_name})" ]]; then
        echo "Killing ${container_name}"
        docker kill ${container_name}
        check_result $? "Problem while killing ${container_name}"
    fi
}

function check_result() {
    if [[ $1 -ne 0 ]]
    then
        echo $2
        exit 1
    fi
}

function set_repo_root_dir() {
    local repo_root_dir=$(git rev-parse --show-toplevel)
    check_result $? "Failed to find repository root directory!"
    echo "${repo_root_dir}"
}

function get_service_version() {
    local repo_dir=$(set_repo_root_dir)
    local service_version=$(cat ${repo_dir}/${MICROSERVICE_VERSION_FILE})
    echo ${service_version}
}

# Pushes container image to the docker registry.
# User must be logged in before pushing.
# Container image is provided as parameter.
#TODO create docker repo
function push_container_image() {

    local repo_web_manifest_uri=$1
    repo_web_manifest_uri=$(echo ${repo_web_manifest_uri} | cut -d "/" -f2-)
    repo_web_manifest_uri="dockerhub/"${repo_web_manifest_uri}
    repo_web_manifest_uri=https://$(echo "${repo_web_manifest_uri}" | sed 's/:/\//g')/manifest.json
    curl --output /dev/null --silent --head --fail ${repo_web_manifest_uri}

    if [[ $? -eq 0 ]]; then
        echo "Image already present in docker repository!"
        exit 1
    fi

    echo "Pushing image: $1"
    docker push "$1"
    check_result $? "Failed to push the image!"
}

# copies pom.xml.template to pom.xml, overriding any existing pom.xml and replaces
# the placeholders with the provided versions.
#
# Only the placeholders are replaced. If the version of a dependency is written
# explicitly, it won't be changed.
#
# arguments:
#  $1 - version of ycsbmgrapi
function replace_artifacts_placeholders() {

    echo "Setting pom.xml"

    local repo_dir=$(set_repo_root_dir)

    cp -f "${repo_dir}/${SOURCE_DIR}/${BACK_END}/${POM_TEMPLATE}" "${repo_dir}/${SOURCE_DIR}/${BACK_END}/pom.xml"
    check_result $? "Failed to create ${repo_dir}/${SOURCE_DIR}/${BACK_END}/pom.xml"
}

# generate pom.xml files with the adequate artifact versions.
#
# find the versions and replace the placeholders.
#
# arguments:
#  $1 - repostiory root
function generate_pom() {
	local repo_dir=$1


    replace_artifacts_placeholders
}
