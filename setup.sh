#!/usr/bin/env bash

mvn clean compile package
MVN_STATUS=$?
[ $MVN_STATUS -eq 0 ] || die "Got build mvn build error status: $MVN_STATUS"