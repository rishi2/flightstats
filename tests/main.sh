#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

# UPDATE BELOW THIS LINE

## TODO - this is not REAL
echo '#    __       _'
echo '#   / _| __ _| | _____'
echo '#  | |_ / _` | |/ / _ \'
echo '#  |  _| (_| |   <  __/'
echo '#  |_|  \__,_|_|\_\___|'
echo



exit 0


## Example: Databunker
## https://github.com/wrsinc/databunker

export APP='retailer'
export ORG_APP="westfield/${APP}"
export GO_GITHUB_DIR="${HOME}/go/src/github.com"

echo "## Create PG database: ${APP}"
echo
PGPASSWORD=postgres createdb \
    -U postgres \
    -h 127.0.0.1 \
    ${APP}
echo


# protobuf: [master]
org_repo='wrsinc/protobuf'
repo_dir="${GO_GITHUB_DIR}/${org_repo}"

echo "## Clone ${org_repo}"
echo
git clone --depth=1 https://github.com/${org_repo}.git "${repo_dir}"
echo


# APP: [CHANGE_BRANCH]
repo_dir="${GO_GITHUB_DIR}/${ORG_APP}"

echo "## Get ${ORG_APP} packages"
echo
cd "${repo_dir}"
go get -d -t ./...
echo


echo "## Run tests"
echo
export DATABUNKER_DATASOURCE_NAME="postgres://postgres:postgres@127.0.0.1/${APP}?sslmode=disable"
export DATABUNKER_PORT=':8010'
export DELETE_EXPIRED_INTERVAL='1s'
export DEBUG_SQL=1
export DATABUNKER_AES_KEY='AES256Key-32Characters1234567890'

go test ./...
