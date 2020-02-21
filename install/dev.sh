#!/usr/bin/env bash
printf "\n\n######## scoring-server dev ########\n"

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

ENV_FILE=${DIR}/../../.env.dev
source ${ENV_FILE}
for ENV_VAR in $(sed 's/=.*//' ${ENV_FILE}); do export "${ENV_VAR}"; done

SCORING_SERVER_PORT=${SCORING_SERVER_PORT:-8084}

cd ${DIR}/..
pwd

npm install
PORT=${SCORING_SERVER_PORT} npm run dev