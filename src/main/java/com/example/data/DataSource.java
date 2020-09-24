package com.example.data;

import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.ImmutablePair;

public interface DataSource {
    // TODO: remove this
    int getMax();

    Stream<String> getData();

    DataInfo getDataInfo(String s);

    ImmutablePair<String,String> parsePayload(String payload);
}
