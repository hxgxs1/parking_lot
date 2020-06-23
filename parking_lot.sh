#!/usr/bin/env bash

#mvn clean install package
if [ -z "$1" ] ; then
        java -jar target/parking_lot-1.0-SNAPSHOT.jar

else
	java -jar target/parking_lot-1.0-SNAPSHOT.jar $arg1
fi