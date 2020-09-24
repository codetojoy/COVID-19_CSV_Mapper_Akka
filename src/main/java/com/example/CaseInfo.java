package com.example;

// import java.util.concurrent.atomic.AtomicInteger;

public class CaseInfo {
    public String caseId;
    public String a;
    public String b;
    public String c;
    public String d;

    /*
    private static AtomicInteger counter = new AtomicInteger(0);

    public CaseInfo() {
        int value = counter.incrementAndGet();
        System.out.println("TRACER CaseInfo ctor counter: " + value);
    }
    */
    public void merge(CaseInfo partialCaseInfo) {
        if (this.caseId == null || partialCaseInfo.caseId == null
                || (! this.caseId.equals(partialCaseInfo.caseId))) {
            throw new IllegalArgumentException("internal error on merge");
        }

        if (partialCaseInfo.a != null) { this.a = partialCaseInfo.a; }
        if (partialCaseInfo.b != null) { this.b = partialCaseInfo.b; }
        if (partialCaseInfo.c != null) { this.c = partialCaseInfo.c; }
        if (partialCaseInfo.d != null) { this.d = partialCaseInfo.d; }
    }

    public static CaseInfo buildPartialCaseInfo(String caseId, String payload) {
        CaseInfo caseInfo = new CaseInfo();
        caseInfo.caseId = caseId;

        String[] payloadTokens = payload.split(Constants.TOKEN_SEPARATOR);

        int index = 0;
        for (String payloadToken : payloadTokens) {
            if ((payloadToken != null) && (! payloadToken.isEmpty())) {
                if (index == 0) { caseInfo.a = payloadToken; }
                if (index == 1) { caseInfo.b = payloadToken; }
                if (index == 2) { caseInfo.c = payloadToken; }
                if (index == 3) { caseInfo.d = payloadToken; }
            }
            index++;
        }

        return caseInfo;
    }

    public String toString() {
        String result = String.format(Constants.CSV_OUTPUT_FORMAT, caseId, a, b, c, d);
        return result;
    }
}
