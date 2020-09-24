## Summary

* work in progress
* experimental parser to explore [this dataset](https://www150.statcan.gc.ca/t1/tbl1/en/tv.action?pid=1310078101) as reported in [this CBC article](https://www.cbc.ca/news/canada/public-health-agency-of-canada-covid-19-statistics-1.5733069)
* the [original project](https://github.com/codetojoy/COVID-19_CSV_Mapper) is not efficient as it is not designed for a large dataset

### To Run

* place full dataset in `./original/data.csv`
* execute: `./run.sh`

### Notes

* the data has multiple rows for various attributes
    - e.g. when `Case information` is `Region`, then `Value` is the region  
    - e.g. when `Case information` is `Age group`, then `Value` is the age group  
    - these attributes are linked via `Case identifier number`
* this code uses an actor for each case id
