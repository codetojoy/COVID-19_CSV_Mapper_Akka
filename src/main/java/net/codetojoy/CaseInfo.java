package net.codetojoy;

import net.codetojoy.util.Constants;

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

    public String toString() {
        String result = String.format(Constants.CSV_OUTPUT_FORMAT, caseId, region, ageGroup, recovered);
        return result;
    }
}
