package com.nenov.appiumframework.appElements;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public abstract class AbstractElement {

    private WebElement element;
    private String elementName = "";

    public AbstractElement(WebElement element) {
        this.element = element;
    }

    public AbstractElement(WebElement element, String elementName) {
        this(element);
        this.elementName = elementName;
    }

    public SearchContext getSearchContext() {
        return element;
    }

    public WebElement getElement() {
        return element;
    }

    public String getElementName() {
        return elementName;
    }

    public boolean isDisplayed() {
        return element != null && element.isDisplayed();
    }
}
