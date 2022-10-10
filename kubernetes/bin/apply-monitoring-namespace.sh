#!/bin/sh
set -eu

ROOT_DIR="$(
	cd "$(dirname -- "$0")"
	cd ..
	pwd
)"

echo "### monitoring namespace"
cd "${ROOT_DIR}"/monitoring
set +e
kubectl apply -f ./monitoring-namespace.yaml
set -eu
echo "###"
echo ""
