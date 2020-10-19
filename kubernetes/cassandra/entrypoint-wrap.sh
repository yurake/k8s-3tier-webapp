#!/bin/bash

exec docker-entrypoint.sh

for f in docker-entrypoint-initdb.d/*; do
  case "$f" in
  *.sh)
    echo "$0: running $f"
    . "$f"
    ;;
  *.cql) echo "$0: running $f" && until cqlsh -f "$f"; do
    echo >&2 "Cassandra is unavailable - sleeping"
    sleep 2
  done &;;
  *) echo "$0: ignoring $f" ;;
  esac
  echo
done
