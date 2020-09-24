#!/bin/bash 

set -e 

./gradlew -q clean cJ

rm out.log 
rm out.csv
./gradlew -q run | tee out.log
cat out.csv 

