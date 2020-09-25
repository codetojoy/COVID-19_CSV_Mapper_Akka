package net.codetojoy.data;

import org.junit.*;
import static org.junit.Assert.*;

public class CsvRowFilterTest {
    private RowFilter rowFilter = new CsvRowFilter();
    private String payload = "";

    @Test
    public void testDoExclude_legal() {
        DataInfo dataInfo = new DataInfo("5150", payload);

        // test
        boolean result = rowFilter.doExclude(dataInfo);

        assertFalse(result);
    }

    @Test
    public void testDoExclude_illegal() {
        DataInfo dataInfo = new DataInfo("N/A", payload);

        // test
        boolean result = rowFilter.doExclude(dataInfo);

        assertTrue(result);
    }

    @Test
    public void testDoExclude_null() {
        DataInfo dataInfo = new DataInfo(null, payload);

        // test
        boolean result = rowFilter.doExclude(dataInfo);

        assertTrue(result);
    }

    @Test
    public void testDoExclude_empty() {
        DataInfo dataInfo = new DataInfo("", payload);

        // test
        boolean result = rowFilter.doExclude(dataInfo);

        assertTrue(result);
    }
}
