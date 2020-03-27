#!/usr/bin/env bash
set -x
oc project scoring 2> /dev/null || oc new-project scoring
oc adm policy add-scc-to-user anyuid -n scoring -z default
oc create -f scoring-deployment.yaml

