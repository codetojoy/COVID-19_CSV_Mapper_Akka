package net.codetojoy;

import net.codetojoy.data.SimpleDataSource;

import java.util.*;
import java.util.stream.*;

import org.junit.*;
import static org.junit.Assert.*;

public class CaseInfoTest {
    @Test
    public void testMerge_region() {
        String caseId = "5150";
        CaseInfo caseInfo = new CaseInfo();
        caseInfo.caseId = caseId;

        CaseInfo partialCaseInfo = new CaseInfo();
        partialCaseInfo.caseId = caseId;
        partialCaseInfo.region = "region1";

        // test
        caseInfo.merge(partialCaseInfo);

        assertEquals("region1", caseInfo.region);
    }

    @Test
    public void testMerge_ageGroup() {
        String caseId = "5150";
        CaseInfo caseInfo = new CaseInfo();
        caseInfo.caseId = caseId;

        CaseInfo partialCaseInfo = new CaseInfo();
        partialCaseInfo.caseId = caseId;
        partialCaseInfo.ageGroup = "ageGroup";

        // test
        caseInfo.merge(partialCaseInfo);

        assertEquals("ageGroup", caseInfo.ageGroup);
    }

    @Test
    public void testMerge_recovered() {
        String caseId = "5150";
        CaseInfo caseInfo = new CaseInfo();
        caseInfo.caseId = caseId;

        CaseInfo partialCaseInfo = new CaseInfo();
        partialCaseInfo.caseId = caseId;
        partialCaseInfo.recovered = "recovered";

        // test
        caseInfo.merge(partialCaseInfo);

        assertEquals("recovered", caseInfo.recovered);
    }
}
