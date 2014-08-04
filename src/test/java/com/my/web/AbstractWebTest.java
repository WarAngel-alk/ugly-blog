package com.my.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

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

    @BeforeClass()
    public void setUp() throws Exception {
        driver = new HtmlUnitDriver();
    }

    @AfterClass()
    public void tearDown() throws Exception {
        driver.quit();
    }

    @BeforeClass(dependsOnMethods = "setUp")
    public void loginBeforeSuite() throws Exception {

    }

}
