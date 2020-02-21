#!/usr/bin/env bash
printf "\n\n######## scoring-server push ########\n"

IMAGE_REPOSITORY=${SCORING_SERVER_IMAGE_REPOSITORY:-quay.io/redhatdemo/2020-scoring-server:latest}

echo "Pushing ${IMAGE_REPOSITORY}"
docker push ${IMAGE_REPOSITORY}
