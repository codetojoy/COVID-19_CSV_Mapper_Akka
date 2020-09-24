package com.example;

import java.util.concurrent.atomic.AtomicInteger;

public class CaseInfo {
    public String caseId;
    public String a;
    public String b;
    public String c;
    public String d;

    private static AtomicInteger counter = new AtomicInteger(0);

    public CaseInfo() {
        int value = counter.incrementAndGet();
        System.out.println("TRACER CaseInfo ctor counter: " + value);
    }

    public static CaseInfo parseStr(String s) {
        String[] tokens = s.split("|");

        String caseId = tokens[0];
        String a = tokens[1];
        String b = tokens[2];
        String c = tokens[3];
        String d = tokens[4];

        CaseInfo caseInfo = new CaseInfo();
        caseInfo.caseId = caseId;
        caseInfo.a = a;
        caseInfo.b = b;
        caseInfo.c = c;
        caseInfo.d = d;

        return caseInfo;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(caseId + "|");
        builder.append(a + "|");
        builder.append(b + "|");
        builder.append(c + "|");
        builder.append(d);
        return builder.toString();
    }
}
