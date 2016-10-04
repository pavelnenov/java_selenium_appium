package com.nenov.appiumframework.annotations;

public class AnnotationGroups {

    //e.g. verifications for inherited activities can be skipped to navigate to some tested screens faster
    public static final String SKIP_INHERITED_TESTS = "skip-inherited-tests";

    //tests which are used to navigate to activities; E.g. they will be run when there are skipped inherited tests which do navigation.
    public static final String NAVIGATION_TEST = "navigation-test";
}
