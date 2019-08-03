#! /bin/bash
#
# Utility to get product information
#

usage() {
    echo "Usage: $0 <name|version|vendor|vcs-ref>"
    exit 1
}

getValue() {
    grep -E ^$1= "$prodinfo" | awk -F= '{ print $2 }'
}

if [ "$#" != "1" ]; then
    usage
fi

prodinfo="$(dirname $0)/../PRODUCT_INFO"

if [ "$1" = "name" -o "$1" = "version" -o "$1" = "vendor" -o "$1" = "vcs-ref" ]; then
    getValue $1
else
    usage
fi

exit 0
