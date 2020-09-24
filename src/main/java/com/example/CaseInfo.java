package com.example;


public class CaseInfo {
    public String caseId;
    public String region;
    public String ageGroup;
    public String recovered;

    public void merge(CaseInfo partialCaseInfo) {
        if (this.caseId == null || partialCaseInfo.caseId == null
                || (! this.caseId.equals(partialCaseInfo.caseId))) {
            throw new IllegalArgumentException("internal error on merge");
        }

        if (partialCaseInfo.region != null) { this.region = partialCaseInfo.region; }
        if (partialCaseInfo.ageGroup != null) { this.ageGroup = partialCaseInfo.ageGroup; }
        if (partialCaseInfo.recovered != null) { this.recovered = partialCaseInfo.recovered; }
    }

    protected static String clean(String s) {
        String result = s;

        if (result.startsWith(Constants.DOUBLE_QUOTE)) {
            result = result.substring(1);
        }

        if (result.endsWith(Constants.DOUBLE_QUOTE)) {
            int lastIndex = result.length() - 1;
            result = result.substring(0, lastIndex);
        }

        return result;
    }

    public static CaseInfo buildPartialCaseInfo(String caseId, String payload) {
        CaseInfo caseInfo = new CaseInfo();
        caseInfo.caseId = caseId;

        if (! payload.trim().isEmpty()) {
            String[] payloadTokens = payload.split(Constants.TOKEN_SEPARATOR);

            String fieldName = clean(payloadTokens[Constants.CSV_MOCK_INDEX_CASE_INFO]);
            String fieldValue = clean(payloadTokens[Constants.CSV_MOCK_INDEX_VALUE]);

            if (fieldName.equals(Constants.FIELD_NAME_REGION)) {
                caseInfo.region = fieldValue;
            } else if (fieldName.equals(Constants.FIELD_NAME_AGE_GROUP)) {
                caseInfo.ageGroup = fieldValue;
            } else if (fieldName.equals(Constants.FIELD_NAME_RECOVERED)) {
                caseInfo.recovered = fieldValue;
            }
        }

        return caseInfo;
    }

    public String toString() {
        String result = String.format(Constants.CSV_OUTPUT_FORMAT, caseId, region, ageGroup, recovered);
        return result;
    }
}
