package com.udacity.jwdnd.course1.cloudstorage.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login {

    @FindBy(id = "inputUsername")
    private WebElement username;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "error-msg")
    private WebElement errorMessage;

    public Login(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
    }

    public void setUsername(String username) {
        this.username.sendKeys(username);
    }

    public void setPassword(String password) {
        this.password.sendKeys(password);
    }

    public void submit(){
        this.password.submit();
    }

    public WebElement getErrorMessage() {
        return errorMessage;
    }
}
