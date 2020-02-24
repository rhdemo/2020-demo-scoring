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
