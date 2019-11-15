#!/bin/bash
docker-compose --no-ansi -f "../example-docker-services/docker-compose.yml" -p example create --build
