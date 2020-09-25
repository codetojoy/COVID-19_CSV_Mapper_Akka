#!/bin/bash 

set -e 

HOME_DIR=$PWD
IN_FILE=$HOME_DIR/original/data1000.csv
OUT_FILE=out.csv
LOG_FILE=out.log

./gradlew -q clean cJ

rm -f $LOG_FILE
rm -f $OUT_FILE

./gradlew -q run --args "$IN_FILE $OUT_FILE" | tee $LOG_FILE

stat $OUT_FILE > /dev/null 2>&1
cat $OUT_FILE

