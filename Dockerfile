# DO NOT EDIT
# Automatically generated from the following template in wrsinc/etc:
# https://github.com/wrsinc/etc/blob/master/roles/docker/templates/Dockerfile.j2

# retailer
FROM maven:alpine

ENV APP=retailer

## java
WORKDIR /usr/src/app
COPY . .
RUN MAVEN_OPTS="-Xms128m -Xmx256m -Xverify:none -XX:+TieredCompilation -XX:TieredStopAtLevel=1" \
    mvn -q -T 1.5C clean install -s settings.xml -DskipTests \
    && rm -f settings.xml

### new relic
RUN curl -sO https://download.newrelic.com/newrelic/java-agent/newrelic-agent/current/newrelic-java.zip \
    && unzip -qq newrelic-java.zip newrelic/newrelic.jar \
    && mv newrelic/newrelic.jar target/ \
    && rm -rf newrelic-java.zip newrelic \
    && touch target/newrelic.yml

ENTRYPOINT ./entrypoint.sh

EXPOSE 8120 8121
