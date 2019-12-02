#! /bin/sh
set -e

helm delete $(helm ls --all --short) --purge || true
docker system prune --all --force

# Build docker images for palisade and store them to the image repo
(cd palisade/local-docker/example && docker-compose -f docker-compose.yml build)

# Build local docker image for Jupyter
(cd palisade && docker build . -t jupyter/base-notebook:latest -f local-docker/example/jupyter/Dockerfile)
