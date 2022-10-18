#!/bin/sh
set -eu

ROOT_DIR="$(
	cd "$(dirname -- "$0")"
	cd ..
	pwd
)"

cd "$ROOT_DIR"/bin

./apply-default-quarkus.sh
./apply-default-option.sh
./apply-monitoring.sh
