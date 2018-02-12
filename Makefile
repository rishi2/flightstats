# DO NOT EDIT
# Automatically generated from the following template in wrsinc/etc:
# https://github.com/wrsinc/etc/blob/master/roles/jenkins/templates/Makefile.j2

# vars
ORG           := westfield
APP           != git config remote.origin.url | sed -e 's|.*[/:]||' -e 's/\..*//'
BRANCH_NAME   ?= $(shell git symbolic-ref -q HEAD | awk -F/ '{print $$NF}')
CHANGE_BRANCH ?= master
PROJECT       ?= minikube
CRED_ENV      := $(shell echo ${PROJECT} | sed -e 's/-.*//')-environment
FIRST_ENV     := minikube
OS            != uname -s
SETTINGS      ?= x
SHELL         := /bin/bash
SHORTSHA      != git rev-parse --short HEAD

# targets
.DEFAULT_GOAL := minikube

build test deploy minikube::
	@set -o errexit
	@set -o nounset
	@set -o pipefail
	@echo "=============================================================================="
	@echo "- ORG:       ${ORG}"
	@echo "- APP:       ${APP}"
	@if [ "${CHANGE_AUTHOR_DISPLAY_NAME}" != "" ]; then \
		echo "- AUTHOR:    ${CHANGE_AUTHOR_DISPLAY_NAME} (@${CHANGE_AUTHOR})"; \
		echo "- TITLE:     ${CHANGE_TITLE}"; \
		echo "- BRANCH:    ${CHANGE_BRANCH}"; \
	fi
	@echo "- BUILD_TAG: ${BUILD_TAG}"
	@echo "- BRANCH/PR: ${BRANCH_NAME}"
	@echo "- SHORTSHA:  ${SHORTSHA}"
	@echo "- ZONE:      ${ZONE}"
	@echo "- OS:        ${OS}"
	@echo "- PATH:      ${PATH}"
	@echo "=============================================================================="
	@echo

build minikube::
	@echo "# BUILD"
	@echo "=============================================================================="
	@echo
	@mkdir -p "${HOME}/go/src/github.com/${ORG}"
	@ln -s "${WORKSPACE}" "${HOME}/go/src/github.com/${ORG}/${APP}" >/dev/null 2>&1 \
		|| true
	@cp "${SETTINGS}" ./settings.xml >/dev/null 2>&1 \
		|| true
	@mkdir -p /tmp/jenkins_info
	@echo ${APP} > /tmp/jenkins_info/job_name
	@docker build \
		--no-cache \
		--quiet \
		--tag gcr.io/${FIRST_ENV}/${APP} \
		.
	@echo

include tests/Makefile

deploy::
	@echo "# DEPLOY: ${PROJECT}"
	@echo "=============================================================================="
	@echo
	@gcloud auth activate-service-account \
		--key-file ${GCLOUD_SERVICE_ACCOUNT_CREDENTIALS} \
		--project ${PROJECT}
	@echo
	@gcloud docker \
		--authorize-only \
		--project ${PROJECT} \
		--quiet
	@echo
	@gcloud container clusters \
		get-credentials ${CRED_ENV} \
		--project ${PROJECT} \
		--zone ${ZONE}
	@echo
	@gcloud docker -- push \
		gcr.io/${PROJECT}/${APP} \
		>/dev/null
	@echo
	@gcloud beta container images -q \
		add-tag \
		gcr.io/${PROJECT}/${APP} \
		gcr.io/${PROJECT}/${APP}:${SHORTSHA}
	@echo
	kubectl set \
		--namespace=${APP} \
		image deployment/${APP} \
		app=gcr.io/${PROJECT}/${APP}:${SHORTSHA}

-include k8s/Makefile

.PHONY: build deploy
