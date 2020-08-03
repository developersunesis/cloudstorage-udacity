package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pageobjects.Home;
import com.udacity.jwdnd.course1.cloudstorage.pageobjects.Login;
import com.udacity.jwdnd.course1.cloudstorage.pageobjects.Register;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestParentSupplier {

    @LocalServerPort
    int port;

    String BASE_URL;

    static WebDriver driver;

    static Login loginPage;

    static Register register;

    static Home home;

    @BeforeAll
    public static void initWebDriver(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @BeforeEach
    public void initPageObjects() {
        BASE_URL = "http://localhost:" + port;

        loginPage = new Login(driver);
        register = new Register(driver);
        home = new Home(driver);
    }

    public static void loginUser(String username, String password){
        loginPage.setUsername(username);
        loginPage.setPassword(password);
        loginPage.submit();
    }

    public static void registerUser(String fname, String lname, String username, String password){
        register.setFirstName(fname);
        register.setLastName(lname);
        register.setUsername(username);
        register.setPassword(password);
        register.submit();
    }

    @AfterAll
    public static void quit() throws InterruptedException {
        //Thread.sleep(120000);
        driver.quit();
    }
}
