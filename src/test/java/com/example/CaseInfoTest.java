package com.example;

import com.example.data.SimpleDataSource;

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

    @Test
    public void testClean_none() {
        String s = "mozart";

        // test
        String result = CaseInfo.clean(s);

        assertEquals(s, result);
    }

    @Test
    public void testClean_begin() {
        String s = "\"mozart";

        // test
        String result = CaseInfo.clean(s);

        assertEquals("mozart", result);
    }

    @Test
    public void testClean_end() {
        String s = "mozart\"";

        // test
        String result = CaseInfo.clean(s);

        assertEquals("mozart", result);
    }

    @Test
    public void testClean_both() {
        String s = "\"mozart\"";

        // test
        String result = CaseInfo.clean(s);

        assertEquals("mozart", result);
    }

    @Test
    public void testBuildPartialCaseInfo_empty() {
        String caseId = "5150";
        String payload = "";

        // test
        CaseInfo result = CaseInfo.buildPartialCaseInfo(caseId, payload);

        assertEquals(caseId, result.caseId);
    }

    @Test
    public void testBuildPartialCaseInfo_basicRegion() {
        String caseId = "5150";
        Stream<String> data = new SimpleDataSource().getData();
        List<String> dataStrings = data.collect(Collectors.toList());
        String payload = dataStrings.get(0);

        // test
        CaseInfo result = CaseInfo.buildPartialCaseInfo(caseId, payload);

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
        CaseInfo result = CaseInfo.buildPartialCaseInfo(caseId, payload);

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
        CaseInfo result = CaseInfo.buildPartialCaseInfo(caseId, payload);

        // 1::"Thu Sep 24 14:24:59 ADT 2020","1","Region","Region1"
        assertEquals(caseId, result.caseId);
        assertEquals("Recovered1", result.recovered);
    }
}
