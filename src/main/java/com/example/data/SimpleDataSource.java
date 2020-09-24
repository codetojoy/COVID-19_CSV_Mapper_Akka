package com.example.data;

import java.util.stream.Stream;
import java.util.*;

import com.example.Constants;

public class SimpleDataSource implements DataSource {
    private static final int NUM_ROWS = 5;
    private static final int NUM_RECORDS_PER_CASE_ID = 4;

    @Override
    public int getMax() {
        return NUM_ROWS * NUM_RECORDS_PER_CASE_ID;
    }

    String populatePayload(int i, int caseId) {
       String a = "";
       String b = "";
       String c = "";
       String d = "";

       if (i == 1) { a = "A" + caseId; }
       if (i == 2) { b = "B" + caseId; }
       if (i == 3) { c = "C" + caseId; }
       if (i == 4) { d = "D" + caseId; }

       return String.format(Constants.PAYLOAD_FORMAT, a, b, c, d);
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
