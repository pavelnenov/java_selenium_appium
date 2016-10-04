package com.nenov.appiumframework.listeners.reportdata;

import java.util.ArrayList;
import java.util.List;

public class TestCasesReport {

    private List<TestCase> testCaseResults;

    public TestCasesReport() {
        testCaseResults = new ArrayList<TestCase>();
    }

    public void addTestCase(TestCase t) {
        testCaseResults.add(t);
    }
}
