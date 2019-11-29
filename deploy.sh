#! /bin/sh
set -e

# If all previous were successful, deploy with helm
(cd charts && helm install egeriapalisade)
