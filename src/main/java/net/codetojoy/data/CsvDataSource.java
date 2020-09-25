package net.codetojoy.data;

import net.codetojoy.util.Constants;

import java.io.*;
import java.util.stream.Stream;
import java.util.*;
import java.nio.file.*;
import java.nio.charset.Charset;

import org.apache.commons.lang3.tuple.ImmutablePair;

public class CsvDataSource implements DataSource {

    protected CsvDataSource() {
    }

    @Override
    public ImmutablePair<String, String> parsePayload(String payload) {
        String[] payloadTokens = payload.split(Constants.TOKEN_SEPARATOR);

        String fieldName = payloadTokens[Constants.CSV_PROD_INDEX_CASE_INFO];
        String fieldValue = payloadTokens[Constants.CSV_PROD_INDEX_VALUE];
        ImmutablePair<String, String> result = new ImmutablePair(fieldName, fieldValue);

        return result;
    }

    @Override
    public DataInfo getDataInfo(String s) {
        String[] tokens = s.split(Constants.TOKEN_SEPARATOR);
        String caseId = tokens[Constants.CSV_PROD_INDEX_CASE_ID];
        DataInfo dataInfo = new DataInfo(caseId, s);
        return dataInfo;
    }

    @Override
    public Stream<String> getData() {
        Stream<String> lines = null;

        try {
            Path file = Paths.get("./oriignal/data1000.csv");
            lines = Files.lines(file, Charset.defaultCharset());
        } catch (IOException ex) {
            System.exit(-1);
        }

        return lines;
    }
}
