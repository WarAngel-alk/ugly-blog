package com.my.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AnonymousWebTest {

    private static String APP_ROOT_URL = "http://localhost:8080/blog";

    private static WebDriver driver;

    @BeforeClass
    public void setUp() throws Exception {
        driver = new HtmlUnitDriver();
    }

    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void testHomePageOpen() throws Exception {
        driver.get(APP_ROOT_URL + "/home");
        assertEquals("Ugly blog", driver.getTitle());

        WebElement postTitleLink = driver.findElement(By.xpath("//div[@class='post-title']/a"));
        postTitleLink.click();

        assertTrue(driver.getCurrentUrl().contains(APP_ROOT_URL + "/post/"));
    }

    @Test
    public void testHomeToPostLink() throws Exception {
        driver.get(APP_ROOT_URL + "/home");

        WebElement postTitleLink = driver.findElement(By.xpath("//div[@class='post-title']/a"));
        postTitleLink.click();

        assertTrue(driver.getCurrentUrl().contains(APP_ROOT_URL + "/post/"));
    }

    @Test
    public void testHomeToPostButton() throws Exception {
        driver.get(APP_ROOT_URL + "/home");

        WebElement postPageButton = driver.findElement(By.xpath("//a/button[@class='btn btn-default']"));
        postPageButton.click();

        assertTrue(driver.getCurrentUrl().contains(APP_ROOT_URL + "/post/"));
    }

    @Test
    public void testDisabledVotingIconsForAnonymous() throws Exception {
        driver.get(APP_ROOT_URL + "/home");

        List<WebElement> elementList = driver.findElements(By.xpath("//div[@class='post-voting']/img[@class='post-vote']"));

        for (WebElement element : elementList) {
            assertTrue(element.getAttribute("src").contains("disabled"));
            assertNull(element.getAttribute("onclick"));
        }
    }
}
