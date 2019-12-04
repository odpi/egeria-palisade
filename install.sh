#!/usr/bin/env bash

cd ..

git clone https://github.com/gchq/Palisade.git
(cd palisade && git checkout develop)

git clone https://github.com/odpi/egeria.git
(cd egeria && git checkout 2f8e861a7204c4bc00ec466fecd3ddf0820168e6)

cd egeria-palisade

./jars.sh && ./images.sh && ./deploy.sh
