package com.nenov.appiumframework.screens;

import com.nenov.appiumframework.config.ConfigProperties;
import com.nenov.appiumframework.driver.Driver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractScreen {

    protected final int WAIT_TIME = 10;
    protected ConfigProperties configProperties = ConfigProperties.getInstance();
    private List<WebElement> activityElements;
    private boolean isActivityLoaded;

    public AbstractScreen() {
        PageFactory.initElements(new AppiumFieldDecorator(Driver.getDriver()), this);
        waitForActivityToLoad();
        checkActivityLoaded();
    }

    public boolean isLoaded() {
        return isActivityLoaded;
    }

    public WebElement getElement(By by) {
        return getElement(by, "[no name specified]");
    }

    public WebElement getElement(By by, String elementName) {
        try {
            return Driver.getDriver().findElement(by);
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * Check if the WebElements are present on the screens to verify it loaded.
     */
    private void waitForActivityToLoad() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), WAIT_TIME);
        activityElements = collectActivityElements();
        wait.until(ExpectedConditions.visibilityOfAllElements(activityElements));
    }

    /**
     * Iterate fields and collect the WebElement ones.
     */
    private List<WebElement> collectActivityElements() {
        List<WebElement> activityElements = new ArrayList<WebElement>();
        Field[] allFields = getClass().getDeclaredFields();
        for (Field field : allFields) {
            if (field.getType().getName().equals(WebElement.class.getName()))
                try {
                    field.setAccessible(true);
                    WebElement we = (WebElement) field.get(this);
                    activityElements.add(we);
                    field.setAccessible(false);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
        }
        return activityElements;
    }

    private void checkActivityLoaded() {
        boolean areAllElementsDisplayed = true;
        for (WebElement el : activityElements) {
            if (el == null || !el.isDisplayed()) {
                areAllElementsDisplayed = false;
                break;
            }
        }
        isActivityLoaded = areAllElementsDisplayed;
    }
}


