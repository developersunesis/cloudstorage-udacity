package com.udacity.jwdnd.course1.cloudstorage;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NoteTests extends TestParentSupplier {

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

        moveToNotesTab();
    }

    private void moveToNotesTab() throws InterruptedException {
        driver.get(BASE_URL + "/home");
        Thread.sleep(500);

        home.navigateToNoteTab();
        Thread.sleep(500);
    }

    // Write a Selenium test that logs in an existing user,
    // creates a note and verifies that the note details are visible in the note list.
    @Test
    @Order(0)
    public void addNote() throws InterruptedException {
        int initialNotesCount = home.getNoteItems().size();

        home.openAddNoteModal();
        Thread.sleep(500);

        home.setNote("Hello Note", "Welcome to the note section");

        moveToNotesTab();
        assertEquals(home.getNoteItems().size(), initialNotesCount + 1);

        initialNotesCount = home.getNoteItems().size();

        home.openAddNoteModal();
        Thread.sleep(500);

        home.setNote("Hello Note 2", "Another note added");

        moveToNotesTab();
        assertEquals(home.getNoteItems().size(), initialNotesCount + 1);

        home.logout();
    }

    // Write a Selenium test that logs in an existing user with existing notes,
    // clicks the edit note button on an existing note, changes the note data, saves the changes,
    // and verifies that the changes appear in the note list.
    @Test
    @Order(1)
    public void editNote() throws InterruptedException {
        // this user should currently have 2 notes from the addNote test
        List<WebElement> notes = home.getNoteItems();
        String title = "Changed Note";
        String content = "Changed Note Data";

        int notePosition = new Random().nextInt(notes.size() - 1);
        WebElement editNoteButton = notes.get(notePosition).findElement(By.id("editBtn"));
        final String initialNoteId = editNoteButton.getAttribute("data-id");
        editNoteButton.click();
        Thread.sleep(500);

        home.setNote(title, content);

        moveToNotesTab();

        boolean wasChanged =false;

        for (WebElement noteItem : home.getNoteItems()) {
            editNoteButton = noteItem.findElement(By.id("editBtn"));
            wasChanged = initialNoteId.equals(editNoteButton.getAttribute("data-id"))
                            && title.equals(editNoteButton.getAttribute("data-title"))
                            && content.equals(editNoteButton.getAttribute("data-description"));

            if(wasChanged) break;
        }

        assertTrue(wasChanged);

        home.logout();
    }

    // Write a Selenium test that logs in an existing user with existing notes, clicks the
    // delete note button on an existing note, and verifies that the note no longer appears in the note list.
    @Test
    @Order(2)
    public void deleteNote() throws InterruptedException {
        List<WebElement> notes = home.getNoteItems();
        int initialNotesCount = notes.size();

        int notePosition = new Random().nextInt(notes.size() - 1);
        WebElement deleteNoteButton = notes.get(notePosition).findElement(By.id("deleteBtn"));

        // For additional check
        String currentDeleteUrl = deleteNoteButton.getAttribute("href");
        deleteNoteButton.click();

        moveToNotesTab();
        assertEquals(home.getNoteItems().size(), initialNotesCount - 1);

        boolean stillExists = false;
        for (WebElement webElement : home.getNoteItems()){
            deleteNoteButton = webElement.findElement(By.id("deleteBtn"));
            stillExists = deleteNoteButton.getAttribute("href").equals(currentDeleteUrl);

            if(stillExists) break;
        }

        assertFalse(stillExists);

        home.logout();
    }
}
