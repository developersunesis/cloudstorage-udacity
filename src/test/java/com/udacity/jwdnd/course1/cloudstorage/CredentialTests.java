package com.udacity.jwdnd.course1.cloudstorage;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CredentialTests extends TestParentSupplier {

    private boolean registeredUser = false;

    @BeforeEach
    public void provideExistingUser() throws InterruptedException {
        if(!registeredUser) {
            driver.get(BASE_URL + "/signup");
            registerUser("Uche", "Emmanuel", "test", "12345678");
            registeredUser = true;
        }

        driver.get(BASE_URL + "/login");
        Thread.sleep(500);
        loginUser("test", "12345678");

        moveToCredentialsTab();
    }

    private void moveToCredentialsTab() throws InterruptedException {
        driver.get(BASE_URL + "/home");
        Thread.sleep(500);

        home.navigateToCredentialTab();
        Thread.sleep(500);
    }

    // Write a Selenium test that logs in an existing user, creates a credential and
    // verifies that the credential details are visible in the credential list.
    @Test
    @Order(0)
    public void addCredential() throws InterruptedException {
        int initialCredentialsCount = home.getCredentialItems().size();

        home.openAddCredentialModal();
        Thread.sleep(500);

        home.setCredential("http://localhost:8080", "uche", "1234568");

        moveToCredentialsTab();
        assertEquals(home.getCredentialItems().size(), initialCredentialsCount + 1);

        initialCredentialsCount = home.getCredentialItems().size();

        home.openAddCredentialModal();
        Thread.sleep(500);

        home.setCredential("http://localhost:8080", "praise", "1dsaAA2");

        moveToCredentialsTab();
        assertEquals(home.getCredentialItems().size(), initialCredentialsCount + 1);

        home.logout();
    }

    // Write a Selenium test that logs in an existing user with existing credentials,
    // clicks the edit credential button on an existing credential, changes
    // the credential data, saves the changes, and verifies that the changes appear in the credential list.
    @Test
    @Order(1)
    public void editCredential() throws InterruptedException {
        // this user should currently have 2 credentials from the addCredential test
        List<WebElement> credentials = home.getCredentialItems();
        String url = "udacity.com";
        String username = "me";
        String password = "just something strange";

        int credentialPosition = new Random().nextInt(credentials.size() - 1);
        WebElement editCredentialButton = credentials.get(credentialPosition).findElement(By.id("editCredentialBtn"));
        final String initialCredentialId = editCredentialButton.getAttribute("data-id");
        editCredentialButton.click();
        Thread.sleep(500);

        home.setCredential(url, username, password);

        moveToCredentialsTab();

        boolean wasChanged =false;

        for (WebElement item : home.getCredentialItems()) {
            editCredentialButton = item.findElement(By.id("editCredentialBtn"));
            wasChanged = initialCredentialId.equals(editCredentialButton.getAttribute("data-id"))
                            && url.equals(editCredentialButton.getAttribute("data-url"))
                            && username.equals(editCredentialButton.getAttribute("data-username"))
                            && password.equals(editCredentialButton.getAttribute("data-password"));

            if(wasChanged) break;
        }

        assertTrue(wasChanged);

        home.logout();
    }

    // Write a Selenium test that logs in an existing user with existing credentials,
    // clicks the delete credential button on an existing credential, and verifies that
    // the credential no longer appears in the credential list.
    @Test
    @Order(2)
    public void deleteCredential() throws InterruptedException {
        List<WebElement> credentials = home.getCredentialItems();
        int initialCredentialsCount = credentials.size();

        int credentialPosition = new Random().nextInt(credentials.size() - 1);
        WebElement deleteCredentialButton = credentials.get(credentialPosition).findElement(By.id("deleteCredentialBtn"));

        // For additional check
        String currentDeleteUrl = deleteCredentialButton.getAttribute("href");
        deleteCredentialButton.click();

        moveToCredentialsTab();
        assertEquals(home.getCredentialItems().size(), initialCredentialsCount - 1);

        boolean stillExists = false;
        for (WebElement webElement : home.getCredentialItems()){
            deleteCredentialButton = webElement.findElement(By.id("deleteCredentialBtn"));
            stillExists = deleteCredentialButton.getAttribute("href").equals(currentDeleteUrl);

            if(stillExists) break;
        }

        assertFalse(stillExists);

        home.logout();
    }
}
