package com.udacity.jwdnd.course1.cloudstorage.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Register {

    @FindBy(id = "inputFirstName")
    private WebElement firstName;

    @FindBy(id = "inputLastName")
    private WebElement lastName;

    @FindBy(id = "inputUsername")
    private WebElement username;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "success-msg")
    private WebElement successMessage;

    public Register(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
    }

    public void setFirstName(String input){
        firstName.sendKeys(input);
    }

    public void setLastName(String input){
        lastName.sendKeys(input);
    }

    public void setUsername(String input){
        username.sendKeys(input);
    }

    public void setPassword(String input){
        password.sendKeys(input);
    }

    public void submit(){
        password.submit();
    }

    public String getResponseMessage(){
        return successMessage.getText();
    }
}
