package com.nenov.appiumframework.listeners;

import com.nenov.appiumframework.config.ConfigProperties;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.Test;
import org.testng.internal.ConstructorOrMethod;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.nenov.appiumframework.annotations.AnnotationGroups.NAVIGATION_TEST;
import static com.nenov.appiumframework.annotations.AnnotationGroups.SKIP_INHERITED_TESTS;

/**
 * This listener can be used when one wants to execute the tests for a specific screens
 * without executing all verification of inherited tests.
 * Only the tests required for navigating to the desired screens under tests will be executed.
 */

public class SkipTestListener implements IInvokedMethodListener {

    private ConfigProperties configProperties;

    public SkipTestListener() {
        super();
        configProperties = ConfigProperties.getInstance();

    }

    public void beforeInvocation(IInvokedMethod invokedMethod, ITestResult result) {
        ITestNGMethod testNgMethod = result.getMethod();
        ConstructorOrMethod contructorOrMethod = testNgMethod.getConstructorOrMethod();
        Test test = contructorOrMethod.getMethod().getAnnotation(Test.class);
        if (test != null) {
            String executedMethodName = invokedMethod.getTestMethod().getMethodName();
            Class executedClassName = result.getTestClass().getRealClass();
            List<Method> declaredMethods = getDeclaredMethods(new LinkedList<Method>(), executedClassName);
            boolean isMethodInherited = isMethodInherited(declaredMethods, executedClassName.getName(), executedMethodName);
            if (configProperties.getSkipInheritedTests() == true) {
                if (isMethodInherited && shouldSkipInheritedTest(test)) {
                    throw new SkipException("Skipping navigation test");
                }
            } else if (shouldSkipNavigationTest(test)) {
                throw new SkipException("Skipping navigation test");
            }
        }
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
    }

    private boolean isMethodInherited(List<Method> declaredMethods, String executedClassName, String methodName) {
        for (Method m : declaredMethods) {
            if (methodName.equals(m.getName()) && m.getDeclaringClass().getName().equals(executedClassName)) {
                return false;
            }
        }
        return true;
    }

    private List<Method> getDeclaredMethods(List<Method> methods, Class<?> type) {
        methods.addAll(Arrays.asList(type.getDeclaredMethods()));
        if (type.getSuperclass() != null && type.getSuperclass()!= Object.class) {
            getDeclaredMethods(methods, type.getSuperclass());
        }
        return methods;
    }

    private boolean hasGroup(Test test, String groupName) {
        for (String group : test.groups()) {
            if (group.equals(groupName)) {
                return true;
            }
        }
        return false;
    }

    private boolean shouldSkipInheritedTest(Test test) {
        return hasGroup(test, SKIP_INHERITED_TESTS);
    }

    private boolean shouldSkipNavigationTest(Test test) {
        return hasGroup(test, NAVIGATION_TEST);
    }

}