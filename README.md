<!-- SPDX-License-Identifier: CC-BY-4.0 -->
<!-- Copyright Contributors to the ODPi Egeria and Palisade project. -->
<p align="center">
  <img src="/logos/PalisadeLogo.svg" width="100" />
  <img src="/logos/ODPi_Egeria_Logo_color.png" width="300" /> 
</p>

# Egeria Palisade integration

Egeria Palisade provides the Apache 2.0 licensed integration between Palisade and Egeria.  
This readme will be split into 2 parts, one focusing on [Egeria](https://github.com/odpi/egeria), and the other, [Palisade](https://github.com/gchq/Palisade). We will cover how to start both services and run through a demo to collect metadata, and redact or mask information based on users context and purpose.  

## Egeria

## Palisade
<p>First things first we need to build Palisade and the Egeria-Palisade 
repository which requires a clone of the Palisade repository in the same folder as this repository.
To build the repositories, first:
</p> 
```
./jars.sh
```  
<p>
This will change directory into the Palisade folder, and build all the artifacts, then change directory into the Egeria Palisade folder and build the artifacts and create the jars needed for the docker images.  
The next step is to create the images needed for helm, this is by compiling but not running the dockerfiles, and can be validated with a `docker images` after running.
</p>
```
./images.sh
```
