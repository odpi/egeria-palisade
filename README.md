<!-- SPDX-License-Identifier: CC-BY-4.0 -->
<!-- Copyright Contributors to the ODPi Egeria and Palisade project. -->
<p align="center">
  <img src="/images/PalisadeLogo.svg" width="100" />
  <img src="/images/ODPi_Egeria_Logo_color.png" width="300" /> 
</p>

# Egeria Palisade integration

Egeria Palisade provides the Apache 2.0 licensed integration between Palisade and Egeria.  
We will cover how to start both services and run through a demo to collect metadata, and redact or mask information based on users context and purpose.  

### Prerequisites
1. [Git](https://git-scm.com/)
1. [Maven](https://maven.apache.org/)
1. [Docker](https://www.docker.com/)
1. [Kubernetes](https://kubernetes.io/)
1. [Helm 3](https://helm.sh/)
1. [Egeria](https://github.com/odpi/egeria)
1. [Palisade](https://github.com/gchq/Palisade)
1. [Egeria-Palisade](https://github.com/odpi/egeria-palisade)


### Quick Start
Assuming you have have installed the prerequisite technologies (Git, Maven, JDK, Docker, Kubernetes, Helm 3) 
and have only cloned this egeria-palisade repo. If that is the case then the following script will download, 
build and deploy all the required stuff to run this demo. 
```bash
./install.sh
```

## Step by Step install
<p>First things first we need to build Egeria, Palisade and the Egeria-Palisade 
repository which requires a clone of the Egeria and Palisade repositories in the same folder as this repository.

```bash
cd ..

git clone https://github.com/gchq/Palisade.git
(cd palisade && git checkout develop)

git clone https://github.com/odpi/egeria.git
(cd egeria && git checkout 2f8e861a7204c4bc00ec466fecd3ddf0820168e6)

cd egeria-palisade
```


To build the repositories and required java artifacts, first run:
This will change directory into the Palisade folder, and build all the artifacts, then change directory into the Egeria Palisade folder and build the artifacts and create the jars needed for the docker images.  
</p>

```bash
./jars.sh 
```

<p>
The next step is to create the images needed for helm, this is by compiling but not running the dockerfiles, and can be validated by running "docker images" after running, which will show a list of all the required docker images.
</p>

```bash
./images.sh 
```
![dockerImages](images/dockerimages.PNG)

Next, you can run deploy the images using helm to create pods containing each of the Palisade services.
```bash
./deploy.sh 
```
Which can be validated by running a `helm list` which returns something similar to the image included.
![dockerImages](images/helmlist.PNG)

Next, run `kubectl get pods` to see all the kubernetes pods created by the helm install.

### Run demo
Once all the pods are ready or complete, open a browser and go to http://localhost:30888
Then follow the 'read-me-first' notebook to guide you through setting up Egeria using the 'egeria-server-config' and 'egeria-server-start' notebooks.
After that you are ready to run the demo by working through the 'restricting-asset-access' notebook.