#!/bin/bash 

set -e 

LOG_FILE=out.log
OUT_FILE=out.csv

./gradlew -q clean cJ

rm -f $LOG_FILE
rm -f $OUT_FILE

HOME_DIR=$PWD
./gradlew -q run --args "$HOME_DIR/original/data1000.csv $OUT_FILE" | tee $LOG_FILE

stat $OUT_FILE > /dev/null 2>&1
ls -l $OUT_FILE

