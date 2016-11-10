#!/bin/bash

set -e -x

pushd source-code
 mvn clean package
popd

#cp source-code/target/concourse-maven-cf-simple-0.0.1-SNAPSHOT.jar  build-output/.
if [ -f source-code/target/*.war ]; then
artifact=$(basename source-code/target/*.war)
cp source-code/target/$artifact  build-output/.
else
artifact=$(basename source-code/target/*.jar)
 cp source-code/target/$artifact  build-output/.
fi
