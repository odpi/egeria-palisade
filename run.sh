#!/usr/bin/env bash

export PALISADE_REST_CONFIG_PATH="/home/jovyan/configRest.json"

java -cp /home/jovyan/example-model-*-shaded.jar uk.gov.gchq.palisade.example.client.ExampleSimpleClient "callie quartile" "/secured/hr/Employees.avro" "HEALTH_SCREENING"
