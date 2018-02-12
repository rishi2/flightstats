#!/bin/sh
# DO NOT EDIT
# Automatically generated from the following template in wrsinc/etc:
# https://github.com/wrsinc/etc/blob/master/roles/docker/templates/entrypoint.sh.j2

# retailer

## java

### extract SNAPSHOT version from pom.xml
JAR_FILE="retailer-$(
  grep -A1 "<artifactId>retailer</artifactId>" pom.xml \
    | awk -F'[<>]' '/<version>.*-SNAPSHOT<\/version>/{print $3}'
).jar"

java \
    -Dgrpc.port=${GRPC_PORT} \
    -Dserver.port=${SERVER_PORT} \
    -Dspring.datasource.url=${DB_CONN_STRING} \
    -Dspring.datasource.username=${POSTGRES_USER} \
    -Dspring.datasource.password=${POSTGRES_PASSWORD} \
    -Dspring.jpa.hibernate.ddl-auto=${DDL_AUTO} \
    -Dlogging.level.root=${LOG_LEVEL_ROOT} \
    -Dlogging.level.io.polarisdev.retailer=${LOG_LEVEL} \
    -Dreceipt.host=${RECEIPT_HOST} \
    -Dreceipt.port=${RECEIPT_PORT} \
    -Dorder.host=${ORDER_HOST} \
    -Dorder.port=${ORDER_PORT} \
    -javaagent:/usr/src/app/target/newrelic.jar \
    -jar /usr/src/app/target/${JAR_FILE}
