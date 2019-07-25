#! /bin/bash

source $(git rev-parse --show-toplevel)/build/build_common.sh

trap clean_up EXIT

readonly SERVICE_VERSION_TEMPLATE="%SERVICE_VERSION%"
readonly WEB_APP_VERSION_TEMPLATE="%WEB_APP_VERSION%"

PACKAGE_CHART=0
PUSH_CHART=0
SKIP_UT=0

#TODO create a helm repo
HELM_REPO="/${MICROSERVICE_NAME}"

BACK_END_VERSION=''
MICROSERVICE_VERSION=''
REPOSITORY_ROOT_DIR=''

function clean_up() {

    echo "Clean up."
    restore_helm_chart_values
}

function usage() {

 cat <<EOF

Usage:
  $(basename $0) [options]

  This script is used to build and upload source images.
  Options:
    --package: Package helm chart.
    --push: Push helm chart.
    --use-production-tags: Only functional user should use this.
    --use-password-from-env: Uses password that is set as an environment variable. If not set user will be prompted for password.
    --skip-ut: skips helm chart design rule compliance check
    --help  | -h : help

EOF
}

function parse_arguments() {

    PARSED_OPTIONS=$(getopt -n "$0" -o h  -l help,package,push -- "$@")
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
            --package ) PACKAGE_CHART=1;;
            --push ) PUSH_CHART=1;;
            -h | --help ) usage;exit 0;;
            -- ) shift;break;;
            * ) "Unsupported parameter $1";exit 2;;
        esac
        shift
    done
}

# find the version of images used in helm chart.
function find_image_versions() {

    BACK_END_VERSION=$(get_service_version)
}


#deletes the chart from chart directory
function delete_packaged_chart() {
    find ${MICROSERVICE_CHART_DIR}/*.tgz
    if [[ $? -eq 0 ]]; then
        echo "Removing *.tgz files from ${MICROSERVICE_CHART_DIR}"
        rm -f ${MICROSERVICE_CHART_DIR}/*.tgz
    fi
}

# Packages microservice chart directory.
# Removes index.yaml and any old chart from the directory before packaging.
function package_chart() {

    delete_packaged_chart
    set_helm_chart_values

    if [[ -f "${MICROSERVICE_CHART_DIR}/requirements.yaml" ]]; then
        echo "This chart directory has requirements file. Running dependency update."
        helm dep update ${MICROSERVICE_CHART_DIR}
        check_result $? "Failed to update dependencies."
    fi

    helm package ${MICROSERVICE_CHART_DIR} -d ${MICROSERVICE_CHART_DIR} --debug
    check_result $? "Failed to package Helm chart."
}

# Set the service and image versions in values.yaml and Chart.yaml.
# Originali values.yaml and Chart.yaml are also backed up so they can
# later be restored.
function set_helm_chart_values() {

    echo "Modifying helm chart values template."
    cp "${MICROSERVICE_CHART_DIR}/values.yaml" "${MICROSERVICE_CHART_DIR}/../values.yaml.bak"
    cp "${MICROSERVICE_CHART_DIR}/Chart.yaml" "${MICROSERVICE_CHART_DIR}/../Chart.yaml.bak"
    if [[ -f "${MICROSERVICE_CHART_DIR}/values.yaml" ]]; then
        echo "Modifying helm chart values template"
		sed -i -e "s/${SERVICE_VERSION_TEMPLATE}/${MICROSERVICE_VERSION}/g" "${MICROSERVICE_CHART_DIR}/values.yaml"
        sed -i -e "s/${WEB_APP_VERSION_TEMPLATE}/${MICROSERVICE_VERSION}/g" "${MICROSERVICE_CHART_DIR}/values.yaml"
    fi
    sed -i -e "s/${SERVICE_VERSION_TEMPLATE}/${MICROSERVICE_VERSION}/g" "${MICROSERVICE_CHART_DIR}/Chart.yaml"
}

# values.yaml and Chart.yaml are restored to original after packaging.
# The point of this is that the local git repo is not impacted after
# chart packaging.
function restore_helm_chart_values() {

    if [[ -f "${MICROSERVICE_CHART_DIR}/../values.yaml.bak" ]]; then
        echo "Restoring values.yaml"
        mv "${MICROSERVICE_CHART_DIR}/../values.yaml.bak" "${MICROSERVICE_CHART_DIR}/values.yaml"
    fi

    if [[ -f "${MICROSERVICE_CHART_DIR}/../Chart.yaml.bak" ]]; then
        echo "Restoring Chart.yaml"
        mv "${MICROSERVICE_CHART_DIR}/../Chart.yaml.bak" "${MICROSERVICE_CHART_DIR}/Chart.yaml"
    fi
}

# Pushes Helm chart from microservice chart directory to Helm repo.
# Chart name can be passed as an argument in case repository contains multiple charts.
function push_chart() {
    #TODO
    echo "Not implemented!"
}

function main() {
    REPOSITORY_ROOT_DIR=$(set_repo_root_dir)
    parse_arguments $@
    MICROSERVICE_VERSION=$(get_service_version)
    MICROSERVICE_CHART_DIR="${REPOSITORY_ROOT_DIR}/${CHARTS}/${MICROSERVICE_NAME}-${CHART}"
    find_image_versions

    if [[ ${PACKAGE_CHART} -eq 1 ]]; then
        package_chart
    fi

    if [[ ${PUSH_CHART} -eq 1 ]]; then
        push_chart
    fi
}
main $@

