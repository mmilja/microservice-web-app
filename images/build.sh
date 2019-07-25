#!/bin/bash

source $(git rev-parse --show-toplevel)/build/build_common.sh

IMAGE_BUILD=0
IMAGE_PUSH=0

VERSION_TAG=''
IMAGE_ARRAY=()
IMAGE_NAME=''
BUILD_DIR=''
IMAGE_DIR=''
BUILD_ARGS=''
VERSION=''
REPOSITORY_ROOT_DIR=''

function usage() {

 cat <<EOF

Usage:
  $(basename $0) [options]

  This script is used to build and upload the select image in util-images.
  Options:
    --build <image_name> | -b <image_name> : Build image with version read from "VERSION" file.
    --push  <image_name> | -p <image_name> : Push image to DockerRepo

                                         Supported image names:
                                         - os-base
                                         - java-base
                                         - ngnix-base
                                         - react-base
    --help  | -h : help

EOF
}

function parse_arguments() {
    PARSED_OPTIONS=$(getopt -n "$0" -o b:p:h  -l help,build:,push: -- "$@")
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
                IMAGE_BUILD=1
                IMAGE_NAME=$1
            ;;
            -p | --push )
                shift;
                IMAGE_PUSH=1
                IMAGE_NAME=$1
            ;;
            -h | --help ) usage;exit 0;;
            -- ) shift;break;;
            * ) "Unsupported parameter $1";exit 2;;
        esac
        shift
    done
}

function build_image() {

    local time_now="$(date -u +'%Y-%m-%dT%H:%M:%SZ')"

    for image in ${IMAGE_ARRAY[@]}; do
        echo "Building image: ${image}"
        docker build -t ${image} ${BUILD_DIR} ${BUILD_ARGS}
        check_result $? "Failed to build ${image} image!"
    done
}

function push_image() {

    for image in ${IMAGE_ARRAY[@]}; do
        echo "Pushing image: ${image}"
        push_container_image ${image}
    done
}

function set_image_version() {

    local util_directory=${REPOSITORY_ROOT_DIR}/${IMAGES}

    case "${IMAGE_NAME}" in
        ${OS_IMAGE_NAME})
            BUILD_DIR="${util_directory}/${BASE_IMAGES}/${OS_IMAGE_NAME}"
            IMAGE_DIR="${OS_IMAGE_NAME}"
            ;;
        ${JAVA_IMAGE_NAME})
            BUILD_DIR="${util_directory}/${BASE_IMAGES}/${JAVA_IMAGE_NAME}"
            IMAGE_DIR="${JAVA_IMAGE_NAME}"
            ;;
        ${NGINX_IMAGE_NAME})
            BUILD_DIR="${util_directory}/${BASE_IMAGES}/${NGINX_IMAGE_NAME}"
            IMAGE_DIR="${NGINX_IMAGE_NAME}"
            ;;
        ${REACT_IMAGE_NAME})
            BUILD_DIR="${util_directory}/${BASE_IMAGES}/${REACT_IMAGE_NAME}"
            IMAGE_DIR="${REACT_IMAGE_NAME}"
            ;;

    esac

    VERSION=$(cat "${BUILD_DIR}/${IMAGE_VERSION_FILE}")
}

function set_image_tag() {

    if [[ "${IMAGE_NAME}" = ${BACK_END_IMAGE_NAME} ]]; then

		VERSION_TAG=${VERSION}
		IMAGE_ARRAY+=(${DOCKER_REPO}:${IMAGE_NAME}-${VERSION_TAG})
    else
        VERSION_TAG=${VERSION}
        IMAGE_ARRAY+=(${DOCKER_REPO}:${IMAGE_NAME}-${VERSION_TAG})
    fi
}

function main() {
    parse_arguments $@
    REPOSITORY_ROOT_DIR=$(set_repo_root_dir)
    set_image_version
    set_image_tag

    if [[ ${IMAGE_BUILD} -eq 1 ]]; then
        build_image
    fi

    if [[ ${IMAGE_PUSH} -eq 1 ]]; then
        push_image
    fi
}

main $@
