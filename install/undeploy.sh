#!/usr/bin/env bash
printf "\n\n######## scoring-server undeploy ########\n"

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

PROJECT=${PROJECT:-scoring}
IMAGE_REPOSITORY=${SCORING_SERVER_IMAGE_REPOSITORY:-quay.io/redhatdemo/2020-scoring-server:latest}

oc project ${PROJECT}
echo "Undeploying ${IMAGE_REPOSITORY}"

oc process -f ${DIR}/scoring-server.yml | oc delete -f -
