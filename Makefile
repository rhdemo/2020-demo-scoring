ENV_FILE := .env.default
ifneq ("$(wildcard $(ENV_FILE))","")
include ${ENV_FILE}
export $(shell sed 's/=.*//'  ${ENV_FILE})
endif

ENV_FILE := .env
ifneq ("$(wildcard $(ENV_FILE))","")
include ${ENV_FILE}
export $(shell sed 's/=.*//' ${ENV_FILE})
endif

##################################

.PHONY: dev
dev:
	mvn quarkus:dev

##################################

.PHONY: build
build:
	mvn clean install

