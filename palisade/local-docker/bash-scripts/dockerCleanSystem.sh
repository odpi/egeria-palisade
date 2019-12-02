#!/bin/bash
set -e
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
. "$DIR/setScriptPath.sh"
"$EXAMPLE/local-docker/bash-scripts/dockerComposeDown.sh"
docker system prune -a
