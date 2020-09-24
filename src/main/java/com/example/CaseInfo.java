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

    public static CaseInfo buildPartialCaseInfo(String caseId, String payload) {
        CaseInfo caseInfo = new CaseInfo();
        caseInfo.caseId = caseId;

        String[] payloadTokens = payload.split(Constants.TOKEN_SEPARATOR);

        int index = 0;
        for (String payloadToken : payloadTokens) {
            if ((payloadToken != null) && (! payloadToken.isEmpty())) {
                if (index == 0) { caseInfo.region = payloadToken; }
                if (index == 1) { caseInfo.ageGroup = payloadToken; }
                if (index == 2) { caseInfo.recovered = payloadToken; }
            }
            index++;
        }

        return caseInfo;
    }

    public String toString() {
        String result = String.format(Constants.CSV_OUTPUT_FORMAT, caseId, region, ageGroup, recovered);
        return result;
    }
}
