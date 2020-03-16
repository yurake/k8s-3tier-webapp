#!/bin/sh
set -eu

ROOT_DIR=$(cd $(dirname -- $0); pwd)

echo "#################"
echo "## test"
echo "#################"
echo ""

echo "#################"
echo "### ab"
echo "#################"
cd ab
docker build -t test/ab:v0.0.1 .
cd ${ROOT_DIR}
echo ""

echo "#################"
echo "#### rabbitmq"
echo "#################"
cd ab
docker run --rm -d --name ab test/ab:v0.0.1 -n 100 -c 100 -p ./sample.json -T "application/json; charset=utf-8" http://jaxrs-quarkus-monitoring:8080/quarkus/rabbitmq/publish
docker logs -f ab
cd ${ROOT_DIR}
echo ""

echo "#################"
echo "#### activemq"
echo "#################"
cd ab
docker run --rm -d --name ab test/ab:v0.0.1 -n 100 -c 100 -p ./sample.json -T "application/json; charset=utf-8" http://jaxrs-quarkus-monitoring:8080/quarkus/activemq/publish
docker logs -f ab
cd ${ROOT_DIR}
echo ""

echo "#################"
echo "#### redis"
echo "#################"
cd ab
docker run --rm -d --name ab test/ab:v0.0.1 -n 100 -c 100 -p ./sample.json -T "application/json; charset=utf-8" http://jaxrs-quarkus-monitoring:8080/quarkus/redis/publish
docker logs -f ab
cd ${ROOT_DIR}
echo ""

echo "#################"
echo "#### hazelcast"
echo "#################"
cd ab
docker run --rm -d --name ab test/ab:v0.0.1 -n 100 -c 100 -p ./sample.json -T "application/json; charset=utf-8" http://jaxrs-quarkus-monitoring:8080/quarkus/hazelcast/publish
docker logs -f ab
cd ${ROOT_DIR}
echo ""

echo "#################"
echo "### docker rmi tag:none"
echo "#################"
docker image prune -f
echo ""
