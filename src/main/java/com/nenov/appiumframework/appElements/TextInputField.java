package com.nenov.appiumframework.appElements;

import com.nenov.appiumframework.logging.Logger;
import org.openqa.selenium.WebElement;

public class TextInputField extends AbstractElement {

    public TextInputField(WebElement element) {
        super(element);
    }

    public TextInputField(WebElement element, String elementName) {
        super(element, elementName);
    }

    public void enterText(String text) {
        Logger.log(String.format("Enter text '%s' in %s field", text, getElementName()));
        getElement().sendKeys(text + "\n");
    }

    public String getText() {
        return getElement().getText();
    }
}
