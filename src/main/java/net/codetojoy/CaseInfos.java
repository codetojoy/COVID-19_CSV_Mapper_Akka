package net.codetojoy;

import net.codetojoy.data.*;
import net.codetojoy.util.*;

import org.apache.commons.lang3.tuple.ImmutablePair;

public class CaseInfos {

    public static CaseInfo buildPartialCaseInfo(String caseId, String payload) {
        DataSource prodDataSource = DataSources.getProdDataSource();
        return buildPartialCaseInfo(caseId, payload, prodDataSource);
    }

    protected static CaseInfo buildPartialCaseInfo(String caseId, String payload, DataSource dataSource) {
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
