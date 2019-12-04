#! /bin/sh
set -e


# If all previous were successful, deploy with helm
(cd charts/egeriapalisade && helm repo add bitnami https://charts.bitnami.com/bitnami)
(cd charts/egeriapalisade && helm dependency update)
(cd charts && helm install lab egeriapalisade)
