#!/bin/bash 

set -e 

./gradlew -q clean cJ

rm -f out.log 
rm -f out.csv
./gradlew -q run | tee out.log
cat out.csv 

