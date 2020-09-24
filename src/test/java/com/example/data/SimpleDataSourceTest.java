package com.example.data;

import java.util.*;
import java.util.stream.*;

import org.junit.*;
import static org.junit.Assert.*;

public class SimpleDataSourceTest {
    private SimpleDataSource simpleDataSource = new SimpleDataSource();

    @Test
    public void testGetData() {
        // test
        Stream<String> result = simpleDataSource.getData();

        List<String> resultStrings = result.collect(Collectors.toList());

        for (String s : resultStrings) {
            System.out.println(s);
        }
    }
}
