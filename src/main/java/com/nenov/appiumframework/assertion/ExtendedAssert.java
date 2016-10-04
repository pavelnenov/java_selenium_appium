package com.nenov.appiumframework.assertion;

import static com.nenov.appiumframework.logging.Logger.log;

public class ExtendedAssert {

    public static void assertTrue(boolean condition, String message) {
        log(String.format("Assert True: %s", message));
        org.testng.Assert.assertTrue(condition, message);
    }

    public static void assertFalse(boolean condition, String message) {
        log(String.format("Assert False: %s", message));
        org.testng.Assert.assertFalse(condition, message);
    }
}
