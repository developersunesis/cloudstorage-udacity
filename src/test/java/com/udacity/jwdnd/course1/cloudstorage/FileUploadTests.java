package com.udacity.jwdnd.course1.cloudstorage;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FileUploadTests extends TestParentSupplier {

    private boolean registeredUser = false;

    @Value("classpath:files/helloworld.txt")
    Resource resource1;

    @Value("classpath:files/example1.PNG")
    Resource resource2;
    
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

        moveToFilesTab();
    }

    private void moveToFilesTab() throws InterruptedException {
        driver.get(BASE_URL + "/home");
        Thread.sleep(500);

        home.navigateToFileTab();
        Thread.sleep(500);
    }

    // Test file upload and duplicate file upload
    @Test
    @Order(0)
    public void addFile() throws InterruptedException, IOException {
        int initialFilesCount = home.getFileItems().size();

        home.setFile(resource1.getFile().getAbsolutePath());

        moveToFilesTab();
        assertEquals(home.getFileItems().size(), initialFilesCount + 1);

        initialFilesCount = home.getFileItems().size();

        home.setFile(resource2.getFile().getAbsolutePath());
        moveToFilesTab();
        assertEquals(home.getFileItems().size(), initialFilesCount + 1);

        // attempt re-upload file 1
        initialFilesCount = home.getFileItems().size();

        home.setFile(resource1.getFile().getAbsolutePath());
        moveToFilesTab();
        assertEquals(home.getFileItems().size(), initialFilesCount);

        home.logout();
    }

    // Delete file test
    @Test
    @Order(1)
    public void deleteFile() throws InterruptedException {
        List<WebElement> files = home.getFileItems();
        int initialFilesCount = files.size();

        int filePosition = new Random().nextInt(files.size() - 1);
        WebElement deleteFileButton = files.get(filePosition).findElement(By.id("deleteFileBtn"));

        // For additional check
        String currentDeleteUrl = deleteFileButton.getAttribute("href");
        deleteFileButton.click();

        moveToFilesTab();
        assertEquals(home.getFileItems().size(), initialFilesCount - 1);

        boolean stillExists = false;
        for (WebElement webElement : home.getFileItems()){
            deleteFileButton = webElement.findElement(By.id("deleteFileBtn"));
            stillExists = deleteFileButton.getAttribute("href").equals(currentDeleteUrl);

            if(stillExists) break;
        }

        assertFalse(stillExists);

        home.logout();
    }
}
