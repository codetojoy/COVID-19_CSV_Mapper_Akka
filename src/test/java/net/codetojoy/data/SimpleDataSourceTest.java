package net.codetojoy.data;

import net.codetojoy.util.*;

import java.util.*;
import java.util.stream.*;

import org.junit.*;
import static org.junit.Assert.*;

import org.apache.commons.lang3.tuple.ImmutablePair;

public class SimpleDataSourceTest {
    private SimpleDataSource simpleDataSource = new SimpleDataSource();

    @Test
    public void testParsePayload_basic() {
        String payload = "\"25-SEP-2020\",\"5150\",\"Region\",\"18\"";

        // test
        ImmutablePair<String, String> result = simpleDataSource.parsePayload(payload);

        String actualLeft = Strings.clean(result.getLeft());
        String actualRight = Strings.clean(result.getRight());

        assertEquals(Constants.FIELD_NAME_REGION, actualLeft);
        assertEquals("18", actualRight);
    }

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
