package com.nenov.appiumframework.driver;

import com.nenov.appiumframework.config.ConfigProperties;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Driver {

    private static AppiumDriver driver = null;
    private static long implicitWaitTime = 15;

    public static void startDriver() {

        if (driver == null) {
            try {

                ConfigProperties configProperties = ConfigProperties.getInstance();
                //set desired capabilities
                DesiredCapabilities capabilities = new DesiredCapabilities();

                if (configProperties.getIsRealDevice() == false) {
                    capabilities.setCapability("avd", configProperties.getDeviceName());
                }
                if (configProperties.getPackagePath() != null && configProperties.getActivityName() != null) {
                    capabilities.setCapability(MobileCapabilityType.APP_PACKAGE, configProperties.getPackagePath());
                    capabilities.setCapability(MobileCapabilityType.APP_ACTIVITY, configProperties.getActivityName());
                } else {
                    File app = new File(configProperties.getApplicationPath());
                    capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
                }
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, configProperties.getPlatform());
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, configProperties.getDeviceName());
                capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, configProperties.getBrowser());
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                capabilities.setCapability("newCommandTimeout", 60 * 50);

                //init driver
                driver = new AndroidDriver(new URL(configProperties.getServerURL()), capabilities);
                driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    public static AppiumDriver getDriver() {
        return driver;
    }

}

