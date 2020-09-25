
package net.codetojoy.util;

public class Constants {
    public static final String CASE_ID_SEPARATOR = "::";
    public static final String TOKEN_SEPARATOR = "\",\"";

    public static final String CSV_OUTPUT_FORMAT = "\"%s\",\"%s\",\"%s\",\"%s\"";
    public static final String PAYLOAD_FORMAT = "%s,%s,%s";

    public static final String ACTOR_NAME_PREFIX = "greeter";

    // REF_DATE, Case Id, Case Info, Value
    public static final String CSV_MOCK_DATA_FORMAT = "\"%s\",\"%s\",\"%s\",\"%s\"";
    public static final int CSV_MOCK_INDEX_CASE_ID = 1;
    public static final int CSV_MOCK_INDEX_CASE_INFO = 2;
    public static final int CSV_MOCK_INDEX_VALUE = 3;

    public static final int CSV_PROD_INDEX_CASE_ID = 3;
    public static final int CSV_PROD_INDEX_CASE_INFO = 4;
    public static final int CSV_PROD_INDEX_VALUE = 11;

    public static final String FIELD_NAME_REGION = "Region";
    public static final String FIELD_NAME_AGE_GROUP = "Age group";
    public static final String FIELD_NAME_RECOVERED = "Recovered";

    public static final String DOUBLE_QUOTE = "\"";

// 0 - "REF_DATE"
// 1 - "GEO"
// 2 - "DGUID"
// 3 - "Case identifier number"
// 4 - "Case information"
// 5 - "UOM"
// 6 - "UOM_ID"
// 7 - "SCALAR_FACTOR"
// 8 - "SCALAR_ID"
// 9 - "VECTOR"
// 10 - "COORDINATE"
// 11 - "VALUE"
// 12 - "STATUS"
// 13 - "SYMBOL"
// 14 - "TERMINATED"
// 15 - "DECIMALS"
}
