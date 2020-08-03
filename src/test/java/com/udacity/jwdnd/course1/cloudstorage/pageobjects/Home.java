package com.udacity.jwdnd.course1.cloudstorage.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Home {

    // Files
    @FindBy(id = "nav-files-tab")
    private WebElement fileTab;

    @FindBy(id = "fileUpload")
    private WebElement fileUpload;

    @FindBy(className = "file-item")
    private List<WebElement> fileItems;

    // Notes
    @FindBy(id = "nav-notes-tab")
    private WebElement noteTab;

    @FindBy(id = "noteModalButton")
    private WebElement addNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleInput;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(className = "note-item")
    private List<WebElement> noteItems;

    // Credentials
    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialTab;

    @FindBy(id = "addCredentialButton")
    private WebElement addCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(className = "credential-item")
    private List<WebElement> credentialItems;

    @FindBy(id = "logoutBtn")
    private WebElement logoutButton;

    public Home(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
    }

    public void navigateToFileTab(){
        fileTab.click();
    }

    public void setFile(String path) {
        fileUpload.clear();
        fileUpload.sendKeys(path);
        fileUpload.submit();
    }

    public List<WebElement> getFileItems() {
        return fileItems;
    }

    public void navigateToNoteTab(){
        noteTab.click();
    }

    public void openAddNoteModal(){
        addNoteButton.click();
    }

    public void setNote(String title, String description){
        noteTitleInput.clear();
        noteTitleInput.sendKeys(title);
        noteDescription.clear();
        noteDescription.sendKeys(description);
        noteDescription.submit();
    }

    public List<WebElement> getNoteItems() {
        return noteItems;
    }


    public void navigateToCredentialTab(){
        credentialTab.click();
    }

    public void openAddCredentialModal(){
        addCredentialButton.click();
    }

    public void setCredential(String url, String username, String password){
        credentialUrl.clear();
        credentialUrl.sendKeys(url);
        credentialUsername.clear();
        credentialUsername.sendKeys(username);
        credentialPassword.clear();
        credentialPassword.sendKeys(password);
        credentialPassword.submit();
    }

    public List<WebElement> getCredentialItems() {
        return credentialItems;
    }


    public void logout(){
        logoutButton.submit();
    }

}
