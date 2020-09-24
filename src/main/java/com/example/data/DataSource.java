package com.example.data;

import java.util.stream.Stream;

public interface DataSource {
    // TODO: remove this
    int getMax();

    Stream<String> getData();

    DataInfo getDataInfo(String s);
}
