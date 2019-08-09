#!/bin/bash

mvn clean install

java -cp target/search-engine-1.0-SNAPSHOT.jar Main $1