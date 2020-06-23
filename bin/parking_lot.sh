#!/usr/bin/env bash

cd ..
#mvn clean install package
if [ -z "$1" ] ; then
    java -jar target/parking_lot-1.0.jar
else
	java -jar target/parking_lot-1.0.jar $1
fi