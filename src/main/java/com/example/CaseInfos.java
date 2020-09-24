package com.example;

import com.example.data.*;

import org.apache.commons.lang3.tuple.ImmutablePair;

public class CaseInfos {
    private static final DataSource dataSource = new SimpleDataSource();

    // TODO: find home for this
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
            ImmutablePair<String, String> pair = dataSource.parsePayload(payload);
            String fieldName = clean(pair.getLeft());
            String fieldValue = clean(pair.getRight());

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
}
