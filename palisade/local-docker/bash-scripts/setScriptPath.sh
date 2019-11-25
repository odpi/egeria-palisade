#!/bin/bash
# sets up the different paths for calling deployment scripts
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
export EXAMPLE="$(cd "$DIR/../.." >/dev/null && pwd -P)"
