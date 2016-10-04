package com.nenov.appiumframework.config;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

public class ConfigProperties {

    private static ConfigProperties instance = null;

    private final String CONFIG_FILE = "config.properties";

    //login info
    private String loginEmail;
    private String loginPassword;

    //config file properties
    private String applicationPath;
    private String platform;

    //properties coming from test suites
    private String serverURL;
    private String deviceName;
    private String browser;
    private String packagePath;
    private String activityName;
    private boolean isRealDevice = false;
    private boolean skipInheritedTests = false;

    private String jsonReportDirname;
    private String failedTestScreenshotsDirname;
    private String elementsScreenshotsDirname;

    private Properties prop;

    private ConfigProperties() {

        prop = new Properties();
        InputStream input = null;

        try {
            //            input = new FileInputStream("config.properties");
            input = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE);
            prop.load(input);

            applicationPath = getStringProperty(prop, "applicationPath");
            platform = getStringProperty(prop, "platformName");
            loginEmail = getStringProperty(prop, "loginEmail");
            loginPassword = getStringProperty(prop, "loginPassword");
            jsonReportDirname = getStringProperty(prop, "jsonReportDirname");
            failedTestScreenshotsDirname = getStringProperty(prop, "failedTestScreenshotsDirname");
            elementsScreenshotsDirname = getStringProperty(prop, "elementsScreenshotsDirname");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static ConfigProperties getInstance() {
        if (instance == null) {
            instance = new ConfigProperties();
        }
        return instance;
    }

    public String getLoginEmail() {
        return loginEmail;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public String getPlatform() {
        return platform;
    }

    public String getApplicationPath() {
        return applicationPath;
    }

    public String getServerURL() {
        return serverURL;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getBrowser() {
        return browser;
    }

    public boolean getIsRealDevice() {
        return isRealDevice;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getJsonReportDirname() {
        return jsonReportDirname;
    }

    public String getElementsScreenshotsDirname() {
        return elementsScreenshotsDirname;
    }

    public String getFailedTestScreenshotsDirname() {
        return failedTestScreenshotsDirname;
    }

    public boolean getSkipInheritedTests() {
        return skipInheritedTests;
    }

    public void setStringProperty(String propName, String value) throws NoSuchFieldException, IllegalAccessException {
        getClass().getDeclaredField(propName).set(this, value);
    }

    public void setBooleanProperty(String propName, String value) throws NoSuchFieldException, IllegalAccessException {
        getClass().getDeclaredField(propName).setBoolean(this, Boolean.parseBoolean(value));
    }

    private boolean getBooleanProperty(Properties prop, String property) {
        String propValue = prop.getProperty(property);
        return propValue.equals("true");
    }

    private String getStringProperty(Properties prop, String property) {
        return prop.getProperty(property);
    }


}

