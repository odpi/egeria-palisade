#!/usr/bin/env bash

export PALISADE_REST_CONFIG_PATH="/home/jovyan/resources/configRest.json"

java -cp /home/jovyan/resources/example-model-*-shaded.jar uk.gov.gchq.palisade.example.client.ExampleSimpleClient $1 $2 $3
