package com.my.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class AnonymousWebTest extends AbstractWebTest {

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
    public void testDisabledPostVotingIconsForAnonymous() throws Exception {
        driver.get(APP_ROOT_URL + "/home");

        List<WebElement> elementList = driver.findElements(By.xpath("//div[@class='post-voting']/img[@class='post-vote']"));

        for (WebElement element : elementList) {
            assertTrue(element.getAttribute("src").contains("disabled.png"));
            assertNull(element.getAttribute("onclick"));
        }
    }

    @Test
    public void testDisabledCommentVotingIconsForAnonymous() throws Exception {
        driver.get(APP_ROOT_URL + "/home");

        // Click link to post page
        driver.findElement(By.xpath("//div[@class='post-title']/a")).click();

        List<WebElement> elementList = driver.findElements(By.xpath("//div[@class='comment-voting']/img[@class='comment-vote']"));

        for (WebElement element : elementList) {
            assertTrue(element.getAttribute("src").contains("disabled.png"));
            assertNull(element.getAttribute("onclick"));
        }
    }

    @Test
    public void testLoginPage() throws Exception {
        driver.get(APP_ROOT_URL + "/home");

        WebElement headerLoginLink = driver.findElement(By.xpath("//a[contains(@href, 'login')]"));
        headerLoginLink.click();

        assertEquals(driver.getCurrentUrl(), APP_ROOT_URL + "/login");

        WebElement loginField = driver.findElement(By.xpath("//input[@name='j_username']"));
        WebElement passwordField = driver.findElement(By.xpath("//input[@name='j_password']"));
        WebElement rememberMeCheckbox = driver.findElement(By.xpath("//input[@name='_spring_security_remember_me']"));
        WebElement formSubmitBtn = driver.findElement(By.xpath("//div[contains(@class, 'form-unit')]/input[@type='submit']"));
    }

    @Test
    public void testSignupPage() throws Exception {
        driver.get(APP_ROOT_URL + "/home");

        WebElement headerLoginLink = driver.findElement(By.xpath("//a[contains(@href, 'signup')]"));
        headerLoginLink.click();

        assertEquals(driver.getCurrentUrl(), APP_ROOT_URL + "/signup");

        WebElement usernameField = driver.findElement(By.xpath("//input[@name='name']"));
        WebElement passwordField = driver.findElement(By.xpath("//input[@name='pass']"));
        WebElement emailField = driver.findElement(By.xpath("//input[@name='email']"));
        WebElement avatarField = driver.findElement(By.xpath("//input[@name='avatarPath']"));
        WebElement formSubmitBtn = driver.findElement(By.xpath("//div[contains(@class, 'form-unit')]/input[@type='submit']"));
    }
}
