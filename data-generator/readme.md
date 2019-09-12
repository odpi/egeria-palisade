<!-- SPDX-License-Identifier: CC-BY-4.0 -->
<!-- Copyright Contributors to the ODPi Egeria project. -->

# Egeria Palisade data generator

CreateCocoEmployeesAvro generates an avro file containing Egerias example company: [Coco Pharmaceuticals personnas](https://github.com/odpi/data-governance/blob/master/docs/coco-pharmaceuticals/personas/README.md).
The avro format is based on employee schema in Palisade. Palisade has a [data generator](https://github.com/gchq/Palisade/tree/develop/example/hr-data-generator) example that generates avro files using the Employee schema.
 <p>
## Build Prereqs:
<ul>
<li>build Palisade as per https://github.com/gchq/Palisade/tree/develop/example/hr-data-generator.</li>
<li>on the same machine then maven clean install this module.</li>
<li>clone https://github.com/odpi/egeria into a folder <egeria></li>
</ul>
## To run the data generation
This class runs as a java application, that takes 2 parameters, the output folder and the folder you have clones Egeria into <egeria>.
 <p>
There is an avro file that has already been generated in data-generator/generated-data. As changes are made in this repository, Egeria or Palisade then this file may need to be re generated.
<p>
Note this file relies on the shape of example Egeria csv files.


