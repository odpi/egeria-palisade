#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
. "$DIR/setScriptPath.sh"
docker-compose --no-ansi -f "$EXAMPLE/local-docker/example/docker-compose.yml" -p example down
