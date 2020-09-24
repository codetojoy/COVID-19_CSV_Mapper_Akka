package com.example.data;

import java.io.*;
import java.util.stream.Stream;
import java.util.*;
import java.nio.file.*;
import java.nio.charset.Charset;

public class CsvDataSource implements DataSource {
    private static final int MAX = 999;

    @Override
    public int getMax() {
        return MAX;
    }

    @Override
    public DataInfo getDataInfo(String s) {
        String[] tokens = s.split("\",\"");
        String caseId = tokens[3];
        DataInfo dataInfo = new DataInfo(caseId, s);
        return dataInfo;
    }

    @Override
    public Stream<String> getData() {
        Stream<String> lines = null;

        try {
            Path file = Paths.get("data1000.csv");
            lines = Files.lines(file, Charset.defaultCharset());
        } catch (IOException ex) {
            System.exit(-1);
        }

        return lines;
    }
}
