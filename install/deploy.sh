#!/usr/bin/env bash
printf "\n\n######## scoring-server deploy ########\n"

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

PROJECT=${PROJECT:-scoring}
IMAGE_REPOSITORY=${SCORING_SERVER_IMAGE_REPOSITORY:-quay.io/redhatdemo/2020-scoring-server:latest}
REPLICAS=${SCORING_SERVER_REPLICAS:-1}

oc project ${PROJECT}
echo "Deploying ${IMAGE_REPOSITORY}"

oc process -f "${DIR}/scoring-server.yml" \
  -p IMAGE_REPOSITORY=${IMAGE_REPOSITORY} \
  -p REPLICAS=${REPLICAS} \
  | oc create -f -
