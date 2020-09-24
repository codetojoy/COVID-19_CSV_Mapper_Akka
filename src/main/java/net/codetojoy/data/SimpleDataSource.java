package net.codetojoy.data;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.stream.Stream;
import java.util.*;

import com.example.Constants;

public class SimpleDataSource implements DataSource {
    private static final int NUM_ROWS = 1000;
    private static final int NUM_RECORDS_PER_CASE_ID = 3;

    @Override
    public int getMax() {
        return NUM_ROWS * NUM_RECORDS_PER_CASE_ID;
    }

    @Override
    public ImmutablePair<String, String> parsePayload(String payload) {
        String[] payloadTokens = payload.split(Constants.TOKEN_SEPARATOR);

        String fieldName = payloadTokens[Constants.CSV_MOCK_INDEX_CASE_INFO];
        String fieldValue = payloadTokens[Constants.CSV_MOCK_INDEX_VALUE];
        ImmutablePair<String, String> result = new ImmutablePair(fieldName, fieldValue);

        return result;
    }

    protected String populatePayload(int i, int caseId) {
        String refDate = new Date().toString();
        String caseIdStr = "" + caseId;
        String fieldName = "";
        String fieldValue = "";

        if (i == 1) {
            fieldName = Constants.FIELD_NAME_REGION;
            fieldValue = Constants.FIELD_NAME_REGION + caseId;
        } else if (i == 2) {
            fieldName = Constants.FIELD_NAME_AGE_GROUP;
            fieldValue = Constants.FIELD_NAME_AGE_GROUP + caseId;
        } else if (i == 3) {
            fieldName = Constants.FIELD_NAME_RECOVERED;
            fieldValue = Constants.FIELD_NAME_RECOVERED + caseId;
        }

        String result = String.format(Constants.CSV_MOCK_DATA_FORMAT, refDate, caseIdStr, fieldName, fieldValue);

        return result;
    }

    @Override
    public DataInfo getDataInfo(String s) {
        String[] tokens = s.split(Constants.CASE_ID_SEPARATOR);
        String caseId = tokens[0];
        String payload = tokens[1];
        DataInfo dataInfo = new DataInfo(caseId, payload);
        return dataInfo;
    }

    @Override
    public Stream<String> getData() {
        List results = new ArrayList<String>();

        for (int caseId = 1; caseId <= NUM_ROWS; caseId++) {
            for (int row = 1; row <= NUM_RECORDS_PER_CASE_ID; row++) {
                String caseIdStr = "" + caseId;
                String payload = populatePayload(row, caseId);
                String result = caseIdStr + Constants.CASE_ID_SEPARATOR + payload;
                results.add(result);
            }
        }

        return results.stream();
    }
}
