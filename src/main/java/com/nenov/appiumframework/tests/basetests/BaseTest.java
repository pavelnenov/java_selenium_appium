package com.nenov.appiumframework.tests.basetests;

import com.nenov.appiumframework.config.ConfigProperties;
import com.nenov.appiumframework.driver.Driver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

/**
 * Accept test parametets from the <suite name>.xml and launch driver the targeted desired capabilities
 */
abstract class BaseTest {

    private final String appBundleID = "com.nenov.appiumframework";

    protected ConfigProperties configProperties;

    @BeforeTest
    @Parameters({ "deviceName", "serverURL", "browser", "isRealDevice", "packagePath", "activityName",
            "SKIP_INHERITED_TESTS" })
    protected void StartDriver(String deviceName, String serverURL,
            @Optional
                    String browser,
            @Optional
                    String isRealDevice,
            @Optional
                    String packagePath,
            @Optional
                    String activityName,
            @Optional
                    String skipInheritedTests) throws IllegalAccessException, NoSuchFieldException {

        configProperties = ConfigProperties.getInstance();
        configProperties.setStringProperty("deviceName", deviceName);
        configProperties.setStringProperty("serverURL", serverURL);
        configProperties.setStringProperty("browser", browser);
        configProperties.setBooleanProperty("isRealDevice", isRealDevice);
        configProperties.setStringProperty("packagePath", packagePath);
        configProperties.setStringProperty("activityName", activityName);
        configProperties.setBooleanProperty("skipInheritedTests", skipInheritedTests);

        Driver.startDriver();

    }

    @AfterTest
    protected void stopDriver() {
        Driver.getDriver().closeApp();
        //        Driver.getDriver().removeApp(appBundleID);
        //        getDriver().close();
        Driver.getDriver().quit();
    }
}
