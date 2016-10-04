package com.nenov.appiumframework.screens;

import com.nenov.appiumframework.appElements.Button;
import com.nenov.appiumframework.appElements.TextInputField;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginScreen extends AbstractScreen {

    @FindBy(id = "username")
    private WebElement userMailWebElement;

    @FindBy(id = "password")
    private WebElement passwordWebElement;

    @FindBy(id = "login")
    private WebElement loginButtonWebElement;

    private final TextInputField userMail;
    private final TextInputField password;
    private final Button loginButton;

    public LoginScreen() {
        userMail = new TextInputField(userMailWebElement, "Username input");
        password = new TextInputField(passwordWebElement, "Password input");
        loginButton = new Button(loginButtonWebElement, "Login");
    }

    public TextInputField getEmailInput() {
        return userMail;
    }

    public TextInputField getPasswordInput() {
        return password;
    }

    public Button getLoginButtonWebElement() {
        return loginButton;
    }
}
