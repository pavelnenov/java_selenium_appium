package com.nenov.appiumframework.listeners.reportdata;

import org.testng.ITestResult;

public class TestCase {

    private String name;
    private String className;
    private String status;
    private String assertMessage;
    private String stacktrace;

    public TestCase(ITestResult tr) {
        this.name = tr.getName();
        this.className = tr.getTestClass().getName();
        this.status = getStatus(tr);
        this.assertMessage = getAssertMessage(tr);
        this.stacktrace = getStacktrace(tr);
    }

    private String getStatus(ITestResult tr) {
        String testStatus;
        switch (tr.getStatus()) {
        case 1:
            testStatus = "Success";
            break;
        case 2:
            testStatus = "Failed";
            break;
        case 3:
            testStatus = "Skipped";
            break;
        default:
            testStatus = "Undefined";
            break;
        }
        return testStatus;
    }

    private String getAssertMessage(ITestResult tr) {
        Throwable t = tr.getThrowable();
        String assertMsg;
        assertMsg = tr.getThrowable() != null ? tr.getThrowable().getMessage() :"";
        return assertMsg;
    }

    private String getStacktrace(ITestResult tr) {
        Throwable throwable = tr.getThrowable();
        String testStackTrace = "";
        if (throwable != null) {
            StringBuilder stackTraceMsg = new StringBuilder();
            String newLineChar = System.getProperty("line.separator");
            for (StackTraceElement el : throwable.getStackTrace()) {
                stackTraceMsg.append(el.toString());
                stackTraceMsg.append(newLineChar);
            }
            testStackTrace = stackTraceMsg.toString();
        }
        return testStackTrace;
    }

}
