#!/bin/bash 

set -e 

./gradlew -q clean cJ

rm -f out.log 
rm -f out.csv
./gradlew -q run --args out.csv | tee out.log
cat out.csv 

