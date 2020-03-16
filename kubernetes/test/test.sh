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
echo ""

echo "#################"
echo "#### rabbitmq"
echo "#################"
cd ab
docker build -t test/ab:v0.0.1 .
docker run --rm -d --name ab test/ab:v0.0.1 -n 100 -c 100 -p ./sample.json -T "application/json; charset=utf-8" http://k8s.3tier.webapp/quarkus/rabbitmq/publish
docker logs -f ab
cd ${ROOT_DIR}
echo ""

echo "#################"
echo "#### activemq"
echo "#################"
cd ab
docker build -t test/ab:v0.0.1 .
docker run --rm -d --name ab test/ab:v0.0.1 -n 100 -c 100 -p ./sample.json -T "application/json; charset=utf-8" http://k8s.3tier.webapp/quarkus/activemq/publish
docker logs -f ab
cd ${ROOT_DIR}
echo ""

echo "#################"
echo "#### redis"
echo "#################"
cd ab
docker build -t test/ab:v0.0.1 .
docker run --rm -d --name ab test/ab:v0.0.1 -n 100 -c 100 -p ./sample.json -T "application/json; charset=utf-8" http://k8s.3tier.webapp/quarkus/redis/publish
docker logs -f ab
cd ${ROOT_DIR}
echo ""

echo "#################"
echo "#### hazelcast"
echo "#################"
cd ab
docker build -t test/ab:v0.0.1 .
docker run --rm -d --name ab test/ab:v0.0.1 -n 100 -c 100 -p ./sample.json -T "application/json; charset=utf-8" http://k8s.3tier.webapp/quarkus/hazelcast/publish
docker logs -f ab
cd ${ROOT_DIR}
echo ""

echo "#################"
echo "### docker rmi tag:none"
echo "#################"
docker image prune -f
echo ""
