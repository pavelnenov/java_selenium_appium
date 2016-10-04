package com.nenov.appiumframework.logging;

import org.testng.Reporter;

public class Logger {

    public static void log(String message) {
        System.out.println(String.format("%s", message));
        Reporter.log(String.format("<p>%s</p>", message));
    }

    public static void log(String tag, String message) {
        System.out.println(String.format("%s: %s", tag, message));
        Reporter.log(String.format("%s: %s", tag, message));
    }
}
