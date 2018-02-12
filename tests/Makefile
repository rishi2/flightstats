test::
	@echo "# TEST"
	@echo "======================================================================"
	@echo
	@if [ "${WRSINC_GITHUB_USERNAME}" == "wrsinc-system" ]; then \
		git config --global \
			url.https://${WRSINC_GITHUB_USERNAME}:${WRSINC_GITHUB_API_TOKEN}@github.com/wrsinc/.insteadOf \
			https://github.com/wrsinc/ >/dev/null 2>&1 ; \
	fi
	@if [ "${WESTFIELD_GITHUB_USERNAME}" == "westfield-ci" ]; then \
		git config --global \
			url.https://${WESTFIELD_GITHUB_USERNAME}:${WESTFIELD_GITHUB_API_TOKEN}@github.com/westfield/.insteadOf \
			https://github.com/westfield/ >/dev/null 2>&1 ; \
	fi
	@if [ -x tests/main.sh ]; then \
		tests/main.sh; \
	else \
		./main.sh; \
	fi
	@echo
	@echo "# TAG"
	@echo "======================================================================"
	@echo
	@for project in ${GCLOUD_PROJECTS}; do \
		docker tag \
			gcr.io/${FIRST_ENV}/${APP} \
			gcr.io/$$project/${APP}; \
	done
	docker images gcr.io/*/${APP}
	@echo

.PHONY: test
