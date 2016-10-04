package com.nenov.appiumframework.appElements;

import com.nenov.appiumframework.logging.Logger;
import org.openqa.selenium.WebElement;

public class Button extends AbstractElement {

    public Button(WebElement element) {
        super(element);
    }

    public Button(WebElement element, String elementName) {
        super(element, elementName);
    }

    public void click() {
        Logger.log(String.format("Click %s", getElementName()));
        getElement().click();
    }
}
