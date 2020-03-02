#!/usr/bin/env bash
printf "\n\n######## scoring-server dev ########\n"

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

ENV_FILE=${DIR}/../../.env.dev
source ${ENV_FILE}
for ENV_VAR in $(sed 's/=.*//' ${ENV_FILE}); do export "${ENV_VAR}"; done

SCORING_SERVER_PORT=${SCORING_SERVER_PORT:-8084}
KAFKA_BROKER_LIST_HOST=${KAFKA_BROKER_LIST_HOST:-0.0.0.0}
KAFKA_BROKER_LIST_PORT=${KAFKA_BROKER_LIST_PORT:-9092}
KAFKA_TRANSACTION_TOPIC=${KAFKA_TRANSACTION_TOPIC:-my-topic}

cd ${DIR}/..
pwd

npm install
PORT=${SCORING_SERVER_PORT} \
KAFKA_BROKER_LIST_HOST=${KAFKA_BROKER_LIST_HOST} \
KAFKA_BROKER_LIST_PORT=${KAFKA_BROKER_LIST_PORT} \
KAFKA_TRANSACTION_TOPIC=${KAFKA_TRANSACTION_TOPIC} \
 npm run dev