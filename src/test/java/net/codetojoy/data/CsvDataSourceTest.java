package net.codetojoy.data;

import net.codetojoy.util.*;

import java.util.*;
import java.util.stream.*;

import org.junit.*;
import static org.junit.Assert.*;

import org.apache.commons.lang3.tuple.ImmutablePair;

public class CsvDataSourceTest {
    private CsvDataSource csvDataSource = new CsvDataSource("null file");

    @Test
    public void testGetDataInfo_basic() {
        String row = "\"2020\",\"Canada\",\"2016A000011124\",\"10\",\"Region\",\"Number\",\"223\",\"units\",\"0\",\"v1158536543\",\"1.10.1\",\"2\",\"\",\"\",\"\",\"0\"";

        // test
        DataInfo result = csvDataSource.getDataInfo(row);

        assertEquals("10", result.caseId);
        assertEquals(row, result.payload);
    }

    @Test
    public void testParsePayload_basic() {
        String payload = "\"2020\",\"Canada\",\"2016A000011124\",\"10\",\"Region\",\"Number\",\"223\",\"units\",\"0\",\"v1158536543\",\"1.10.1\",\"2\",\"\",\"\",\"\",\"0\"";

        // test
        ImmutablePair<String, String> result = csvDataSource.parsePayload(payload);

        String actualLeft = Strings.clean(result.getLeft());
        String actualRight = Strings.clean(result.getRight());

        assertEquals(Constants.FIELD_NAME_REGION, actualLeft);
        assertEquals("2", actualRight);
    }
}
