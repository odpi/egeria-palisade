#!/usr/bin/env bash

export PALISADE_REST_CONFIG_PATH="configRest.json"

java -cp palisade/example-model/target/example-model-*-shaded.jar uk.gov.gchq.palisade.example.client.ExampleSimpleClient "callie quartile" "/secured/hr/Employees.avro" "HEALTH_SCREENING"
