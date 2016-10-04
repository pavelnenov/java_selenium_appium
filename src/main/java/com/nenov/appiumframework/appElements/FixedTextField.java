package com.nenov.appiumframework.appElements;

import org.openqa.selenium.WebElement;


public class FixedTextField extends AbstractElement{

    public FixedTextField(WebElement element) {
        super(element);
    }

    public FixedTextField(WebElement element, String elementName) {
        super(element, elementName);
    }

    public String getText() {
        return getElement().getText().trim();
    }
}
