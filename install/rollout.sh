#!/usr/bin/env bash
printf "\n\n######## scoring-server rollout ########\n"

echo "Rolling out new version of scoring-server"
oc rollout latest dc/scoring-server
