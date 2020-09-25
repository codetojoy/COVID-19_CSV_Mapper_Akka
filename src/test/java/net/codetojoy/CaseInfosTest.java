package net.codetojoy;

import net.codetojoy.data.SimpleDataSource;

import java.util.*;
import java.util.stream.*;

import org.junit.*;
import static org.junit.Assert.*;

public class CaseInfosTest {

    @Test
    public void testBuildPartialCaseInfo_empty() {
        String caseId = "5150";
        String payload = "";

        // test
        CaseInfo result = CaseInfos.buildPartialCaseInfo(caseId, payload);

        assertEquals(caseId, result.caseId);
    }

    @Test
    public void testBuildPartialCaseInfo_basicRegion() {
        String caseId = "5150";
        Stream<String> data = new SimpleDataSource().getData();
        List<String> dataStrings = data.collect(Collectors.toList());
        String payload = dataStrings.get(0);

        // test
        CaseInfo result = CaseInfos.buildPartialCaseInfo(caseId, payload);

        // 1::"Thu Sep 24 14:24:59 ADT 2020","1","Region","Region1"
        assertEquals(caseId, result.caseId);
        assertEquals("Region1", result.region);
    }

    @Test
    public void testBuildPartialCaseInfo_basicAgeGroup() {
        String caseId = "5150";
        Stream<String> data = new SimpleDataSource().getData();
        List<String> dataStrings = data.collect(Collectors.toList());
        String payload = dataStrings.get(1);

        // test
        CaseInfo result = CaseInfos.buildPartialCaseInfo(caseId, payload);

        // 1::"Thu Sep 24 14:24:59 ADT 2020","1","Region","Region1"
        assertEquals(caseId, result.caseId);
        assertEquals("Age Group1", result.ageGroup);
    }

    @Test
    public void testBuildPartialCaseInfo_basicRecovered() {
        String caseId = "5150";
        Stream<String> data = new SimpleDataSource().getData();
        List<String> dataStrings = data.collect(Collectors.toList());
        String payload = dataStrings.get(2);

        // test
        CaseInfo result = CaseInfos.buildPartialCaseInfo(caseId, payload);

        // 1::"Thu Sep 24 14:24:59 ADT 2020","1","Region","Region1"
        assertEquals(caseId, result.caseId);
        assertEquals("Recovered1", result.recovered);
    }
}
