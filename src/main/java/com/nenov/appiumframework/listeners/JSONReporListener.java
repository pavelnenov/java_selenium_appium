package com.nenov.appiumframework.listeners;

import com.google.gson.GsonBuilder;
import com.nenov.appiumframework.config.ConfigProperties;
import com.nenov.appiumframework.driver.Driver;
import com.nenov.appiumframework.listeners.reportdata.TestCase;
import com.nenov.appiumframework.listeners.reportdata.TestCasesReport;
import com.nenov.appiumframework.util.TestUtils;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import static com.nenov.appiumframework.logging.Logger.log;

public class JSONReporListener extends TestListenerAdapter {


    private ConfigProperties configProperties;
    private TestCasesReport testCasesReport;

    public JSONReporListener() {
        super();
        configProperties = ConfigProperties.getInstance();
        testCasesReport = new TestCasesReport();
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        logTest(tr);
        createScreenshot(tr);
        log("\n" + tr.getName() + " -- Test method failed\n");
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        logTest(tr);
        log("\n" + tr.getName() + " -- Test method skipped\n");
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        logTest(tr);
        log("\n" + tr.getName() + " -- Test method success\n");
    }

    private String generateJSONReportName(ITestResult tr) {
        String outputDir = tr.getTestContext().getOutputDirectory();
        String suiteName = tr.getTestContext().getSuite().getName();
        String dateTimeStamp = TestUtils.getCurrentDateTime();
        Object[] args = { outputDir, File.separator, configProperties.getJsonReportDirname(), suiteName, dateTimeStamp };
        MessageFormat mf = new MessageFormat("{0}{1}{2}{1}{3}_{4}.json");
        return mf.format(args);
    }

    private String generateScreenshotFileName(ITestResult tr) {
        String className = tr.getTestClass().getName().substring(tr.getTestClass().getName().lastIndexOf('.') + 1);
        String suiteName = tr.getTestContext().getSuite().getName();
        String dateTimeStamp = TestUtils.getCurrentDateTime();
        MessageFormat mf = new MessageFormat("{0}{1}{2}{1}{3}_{4}.png");
        Object[] args = { className, "_", suiteName, tr.getMethod().getMethodName(), dateTimeStamp };
        return mf.format(args);
    }

    private void logTest(ITestResult tr) {
        testCasesReport.addTestCase(new TestCase(tr));
        log("Writing JSON Object to File...");
        TestUtils.writeToFile(generateJSONReportName(tr),
                new GsonBuilder().setPrettyPrinting().create().toJson(testCasesReport));
    }

    private void createScreenshot(ITestResult tr) {
        File screenshotsDir = new File(tr.getTestContext().getOutputDirectory(), configProperties.getFailedTestScreenshotsDirname());
        File screenshotFile = new File(screenshotsDir, generateScreenshotFileName(tr));

        File scrFile = null;
        try {
            scrFile = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(scrFile, screenshotFile);
            } catch (IOException ioe) {
                log("Screenshot file copy has failed!");
                log("Stacktrace", ioe.getMessage());
            }
        } catch (WebDriverException screenshotException) {
            log("Error: Screenshot exception!");
            log("Stacktrace", screenshotException.getMessage());
        }
    }
}

