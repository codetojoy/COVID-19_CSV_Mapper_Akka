#!/bin/bash

sort out.csv > sort.out.csv
diff sort.out.csv ../COVID-19_CSV_Mapper/sort.out.csv > sort.diff.txt
wc -l sort.diff.txt 

