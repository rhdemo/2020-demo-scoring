export $(shell sed 's/=.*//' .env.default)

ENV_FILE := .env
ifneq ("$(wildcard $(ENV_FILE))","")
include ${ENV_FILE}
export $(shell sed 's/=.*//' ${ENV_FILE})
endif

##################################

# DEV - run apps locally for development

.PHONY: dev
dev:
	./install/dev.sh

##################################

# BUILD - build images locally using s2i

.PHONY: build
build:
	./install/build.sh

##################################

# PUSH - push images to repository

.PHONY: push
push:
	./install/push.sh

##################################

# LOGIN - log in to OpenShift cluster to deploy/rollout/undeploy
# requires .env OC_ variables (e.g. OC, OC_URL, OC_USER, OC_PASSWORD)

.PHONY: login
login:
	./common/install/login.sh

##################################

# DEPLOY - deploy applications to an openshift cluster
# requires LOGIN prerequisites

.PHONY: deploy
deploy: login
	./install/deploy.sh

##################################

# ROLLOUT - refresh existing deployment
# requires LOGIN prerequisites

.PHONY: rollout
rollout: login
	./install/rollout.sh

##################################

# UNDEPLOY - remove deployed frontend deployment artifacts
# requires LOGIN prerequisites

.PHONY: undeploy
undeploy: login
	./install/undeploy.sh

##################################

# DELETE - delete frontend namespace
# requires LOGIN prerequisites

.PHONY: delete
delete:
	${OC} login ${OC_URL} -u ${OC_USER} -p ${OC_PASSWORD} --insecure-skip-tls-verify=true
	${OC} project ${PROJECT} 2> /dev/null && oc delete project ${PROJECT}