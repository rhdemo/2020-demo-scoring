#!/usr/bin/env bash
printf "\n\n######## scoring-server build ########\n"

IMAGE_REPOSITORY=${SCORING_SERVER_IMAGE_REPOSITORY:-quay.io/redhatdemo/2020-scoring-server:latest}
SOURCE_REPOSITORY_URL=${SOURCE_REPOSITORY_URL:-git@github.com:rhdemo/2020-demo-scoring.git}
SOURCE_REPOSITORY_REF=${SOURCE_REPOSITORY_REF:-master}

echo "Building ${IMAGE_REPOSITORY}/scoring-server from ${SOURCE_REPOSITORY_URL} on ${SOURCE_REPOSITORY_REF}"

s2i build ${SOURCE_REPOSITORY_URL} --ref ${SOURCE_REPOSITORY_REF} ubi8/nodejs-12 ${IMAGE_REPOSITORY}
