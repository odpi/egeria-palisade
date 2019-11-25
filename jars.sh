#! /bin/sh
set -e

# We want to build palisade with egeria supplying the resource service
# This means:
# * we need to adapt the User datatype and its associated fields for egeria
# * we need to build a resource-service rest client pointing to egeria

# Start with a clean build space
(cd palisade && mvn clean)

# Build palisade without examples supplying most common dependencies except Employee datatype
(cd palisade && mvn install -Pquick)

# Start with a clean build space
(cd egeria-palisade/palisade && mvn clean)

# Build hr-data-generator supplying Employee datatype
(cd palisade && mvn install -pl example/hr-data-generator -Dmaven.test.skip=true)

# Build egeria-palisade example-model supplying User datatype
(cd egeria-palisade/palisade && mvn install -pl example-model -Dmaven.test.skip=true)

# Build palisade example-services supplying Example REST services
(cd palisade && mvn install -pl example/example-services -amd -Dmaven.test.skip=true)

# Build egeria-palisade supplying docker deployment etc.
(cd egeria-palisade/palisade && mvn install -Dmaven.test.skip=true)
