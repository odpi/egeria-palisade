#! /bin/sh
set -e

# Build docker images for palisade and store them to the image repo
(cd palisade/local-docker/example && docker-compose -f docker-compose.yml build)
