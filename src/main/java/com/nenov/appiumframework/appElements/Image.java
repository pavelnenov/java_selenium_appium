package com.nenov.appiumframework.appElements;

import org.openqa.selenium.WebElement;

//TODO at this point the class does not provide any functionality
public class Image extends AbstractElement{
    public Image(WebElement element) {
        super(element);
    }

    public Image(WebElement element, String elementName) {
        super(element, elementName);
    }
}
