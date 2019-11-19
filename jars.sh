#! /bin/sh
set -e

# Start with a clean build space
(cd palisade && mvn clean)
(cd egeria-palisade/palisade && mvn clean)

# Build palisade without examples
(cd palisade && mvn install -Pquick)

# Build hr-data-generator supplying Employee datatype
(cd palisade && mvn install -pl example/hr-data-generator -Dmaven.test.skip=true)

# 
(cd egeria-palisade/palisade && mvn install -pl example-model -Dmaven.test.skip=true)

(cd palisade && mvn install -pl example/example-services -Dmaven.test.skip=true)

(cd egeria-palisade/palisade && mvn install -Dmaven.test.skip=true)
