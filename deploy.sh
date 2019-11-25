#! /bin/sh
set -e

# If all previous were successful, deploy with helm
(cd egeria-palisade/charts && helm install egeriapalisade)
