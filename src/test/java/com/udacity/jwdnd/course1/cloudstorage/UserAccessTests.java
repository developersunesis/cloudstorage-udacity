package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

class UserAccessTests extends TestParentSupplier {

	// Write a Selenium test that verifies that the home page is
	// not accessible without logging in.
	@Test
	public void testHomePageNotAccessible() {
		driver.get(BASE_URL + "/home");
		assertNotEquals("Home", driver.getTitle());
	}

	// Write a Selenium test that signs up a new user, logs that user in,
	// verifies that they can access the home page, then logs out and verifies
	// that the home page is no longer accessible.
	@Test
	public void testUserRegistrationAndLogin() throws InterruptedException {
		driver.get(BASE_URL + "/signup");
		registerUser("Uche", "Emmanuel", "testuser", "12345678");
		assertEquals("You successfully signed up! Please continue to the login page.", register.getResponseMessage());

		driver.get(BASE_URL + "/login");
		Thread.sleep(1000);
		loginUser("test", "12345678");
		assertThrows(NoSuchElementException.class, () -> loginPage.getErrorMessage().getText());
		assertEquals("Home", driver.getTitle());

		// logout
		Thread.sleep(1000);
		home.logout();
		driver.get(BASE_URL + "/home");
		assertNotEquals("Home", driver.getTitle());
	}
}
