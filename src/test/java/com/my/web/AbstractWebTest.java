package com.my.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

public abstract class AbstractWebTest {

    protected static WebDriver driver;

    protected static String APP_ROOT_URL = "http://localhost:8080/blog";

    /**
     * relative path should starts with '/'.<br/>
     * returns APP_ROOT_URL + relativePath
     */
    protected String getAbsolutePath(String relativePath) {
        return APP_ROOT_URL + relativePath;
    }

    @BeforeSuite()
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
    }

    @AfterSuite
    public void tearDown() throws Exception {
        driver.quit();
    }

    @BeforeClass
    public void loginBeforeClass() throws Exception {

    }

    protected void loginInternal(String username, String password, boolean rememberMe) {
        driver.get(getAbsolutePath("/login"));

        WebElement loginField = driver.findElement(By.xpath(loginPage_UsernameField));
        WebElement passwordField = driver.findElement(By.xpath(loginPage_PasswordField));
        WebElement rememberMeCheckbox = driver.findElement(By.xpath(loginPage_RememberMeCheckbox));
        WebElement formSubmitBtn = driver.findElement(By.xpath(loginPage_SubmitFormButton));

        loginField.sendKeys(username);
        passwordField.sendKeys(password);
        if (rememberMe) {
            rememberMeCheckbox.click();
        }
        formSubmitBtn.submit();
    }

    @AfterClass
    public void logoutAfterClass() throws Exception {

    }
}
