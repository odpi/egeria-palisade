#!/usr/bin/env bash

export PALISADE_REST_CONFIG_PATH="/home/jovyan/resources/configRest.json"

java -cp /home/jovyan/resources/example-model-*-shaded.jar uk.gov.gchq.palisade.example.client.ExampleSimpleClient "$1" "$2" "$3" | \
sed $'s/uid=/\\\n\\nuid=/'| \
sed $'s/,name=/\\\nname=/g'| \
sed $'s/,dateOfBirth=/\\\ndateOfBirth=/g'| \
sed $'s/,contactNumbers=/\\\ncontactNumbers=/'| \
sed $'s/,emergencyContacts=/\\\nemergencyContacts=/g'| \
sed $'s/,address=/\\\naddress=/g'| \
sed $'s/,bankDetails=/\\\nbankDetails=/g'| \
sed $'s/,taxCode=/\\\ntaxCode=/g'| \
sed $'s/,nationality=/\\\nnationality=/g'| \
sed $'s/,manager=/\\\nmanager=/'| \
sed $'s/,hireDate=/\\\nhireDate=/g'| \
sed $'s/,grade=/\\\ngrade=/g'| \
sed $'s/,department=/\\\ndepartment=/g'| \
sed $'s/,salaryAmount=/\\\nsalaryAmount=/g'| \
sed $'s/,salaryBonus=/\\\nsalaryBonus=/g'| \
sed $'s/,workLocation=/\\\nworkLocation=/g'| \
sed $'s/,sex=/\\\nsex=/g'| \
sed $'s/contactName=/\\\ncontactName=/g'
