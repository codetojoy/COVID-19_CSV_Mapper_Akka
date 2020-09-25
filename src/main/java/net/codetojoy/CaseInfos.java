package net.codetojoy;

import net.codetojoy.data.*;
import net.codetojoy.util.*;

import org.apache.commons.lang3.tuple.ImmutablePair;

public class CaseInfos {
    private static final DataSource dataSource = new SimpleDataSource();

    public static CaseInfo buildPartialCaseInfo(String caseId, String payload) {
        CaseInfo caseInfo = new CaseInfo();
        caseInfo.caseId = caseId;

        if (! payload.trim().isEmpty()) {
            ImmutablePair<String, String> pair = dataSource.parsePayload(payload);
            String fieldName = Strings.clean(pair.getLeft());
            String fieldValue = Strings.clean(pair.getRight());

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
