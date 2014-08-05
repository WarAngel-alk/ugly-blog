package com.my.web;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class UserWebTest extends AbstractWebTest {

    private final String commentText = "Test comment text" + RandomStringUtils.randomNumeric(10);
    private final String messageSubject = "TEST MESSAGE SUBJECT" + RandomStringUtils.randomNumeric(10);
    private final String messageText = "TEST MESSAGE TEXT" + RandomStringUtils.randomNumeric(10);

    @Test(priority = 1)
    public void testUserLogin() throws Exception {
        driver.get(getAbsolutePath("/login"));

        WebElement loginField = driver.findElement(By.xpath("//input[@name='j_username']"));
        WebElement passwordField = driver.findElement(By.xpath("//input[@name='j_password']"));
        WebElement rememberMeCheckbox = driver.findElement(By.xpath("//input[@name='_spring_security_remember_me']"));
        WebElement formSubmitBtn = driver.findElement(By.xpath("//div[contains(@class, 'form-unit')]/input[@type='submit']"));

        loginField.sendKeys("user_2");
        passwordField.sendKeys("user_2");
        rememberMeCheckbox.click();
        formSubmitBtn.submit();

        assertEquals(driver.getCurrentUrl(), getAbsolutePath("/"));

        assertNotNull(driver.manage().getCookieNamed("JSESSIONID"));
        assertNotNull(driver.manage().getCookieNamed("SPRING_SECURITY_REMEMBER_ME_COOKIE"));
    }

    @Test(priority = 2)
    public void testPostVotingIconsForUser() throws Exception {
        driver.get(APP_ROOT_URL + "/home");

        List<WebElement> elementList = driver.findElements(By.xpath("//div[@class='post-voting']/img[@class='post-vote']"));

        assertTrue(elementList.size() >= 2);
        assertTrue(elementList.size() % 2 == 0);

        for (int i = 0; i < elementList.size(); ) {
            WebElement voteUp = elementList.get(i++);
            WebElement voteDown = elementList.get(i++);
            String voteUpSrc = voteUp.getAttribute("src");
            String voteDownSrc = voteDown.getAttribute("src");

            assertTrue(voteUpSrc.contains("active.png") || voteUpSrc.contains("inactive.png"));
            assertTrue(voteDownSrc.contains("active.png") || voteDownSrc.contains("inactive.png"));

            if (voteUpSrc.contains("active.png") || voteDownSrc.contains("active.png")) {
                assertNull(voteUp.getAttribute("onclick"));
                assertNull(voteDown.getAttribute("onclick"));
            } else {
                assertNotNull(voteUp.getAttribute("onclick"));
                assertNotNull(voteDown.getAttribute("onclick"));
            }
        }
    }

    @Test(priority = 2)
    public void testCommentVotingIconsForUser() throws Exception {
        driver.get(APP_ROOT_URL + "/home");

        // Click link to post page
        driver.findElement(By.xpath("//div[@class='post-title']/a")).click();

        List<WebElement> elementList = driver.findElements(By.xpath("//div[@class='comment-voting']/img[@class='comment-vote']"));

        assertTrue(elementList.size() >= 2);
        assertTrue(elementList.size() % 2 == 0);

        for (int i = 0; i < elementList.size(); ) {
            WebElement voteUp = elementList.get(i++);
            WebElement voteDown = elementList.get(i++);
            String voteUpSrc = voteUp.getAttribute("src");
            String voteDownSrc = voteDown.getAttribute("src");

            assertTrue(voteUpSrc.contains("_active.png") || voteUpSrc.contains("_inactive.png"));
            assertTrue(voteDownSrc.contains("_active.png") || voteDownSrc.contains("_inactive.png"));

            if (voteUpSrc.contains("_active.png") || voteDownSrc.contains("_active.png")) {
                assertNull(voteUp.getAttribute("onclick"));
                assertNull(voteDown.getAttribute("onclick"));
            } else {
                assertNotNull(voteUp.getAttribute("onclick"));
                assertNotNull(voteDown.getAttribute("onclick"));
            }
        }
    }

    @Test(priority = 2)
    public void testNewCommentFormExists() throws Exception {
        driver.get(getAbsolutePath("/home"));
        // Click link to post page
        driver.findElement(By.xpath("//div[@class='post-title']/a")).click();

        assertTrue(driver.getCurrentUrl().contains(getAbsolutePath("/post")));

        WebElement newCommentForm = driver.findElement(By.xpath("//form[@id='newComment']"));
        WebElement newCommentText = driver.findElement(By.xpath("//form[@id='newComment']//textarea[@id='text']"));
        WebElement newCommentSubmit = driver.findElement(By.xpath("//form[@id='newComment']//input[@type='submit']"));
    }

    @Test(priority = 2)
    public void testNewCommentAdding() throws Exception {
        driver.get(getAbsolutePath("/home"));
        // Click link to post page
        driver.findElement(By.xpath("//div[@class='post-title']/a")).click();

        assertTrue(driver.getCurrentUrl().contains(getAbsolutePath("/post/")));

        WebElement newCommentText = driver.findElement(By.xpath("//form[@id='newComment']//textarea[@id='text']"));
        WebElement newCommentSubmit = driver.findElement(By.xpath("//form[@id='newComment']//input[@type='submit']"));

        newCommentText.sendKeys(commentText);
        newCommentSubmit.submit();

        assertTrue(driver.getCurrentUrl().contains(getAbsolutePath("/post/")));

        String source = driver.getPageSource();

        WebElement newlyAddedComment = driver.findElement(By.xpath(
                "//div[@class='comment']//div[@class='comment-content' and contains(text(), '" + commentText + "')]"));
    }

    @Test(priority = 2, dependsOnMethods = "testNewCommentAdding", expectedExceptions = NoSuchElementException.class)
    public void testOwnCommentDeleting() throws Exception {
        driver.get(getAbsolutePath("/home"));
        // Click link to post page
        driver.findElement(By.xpath("//div[@class='post-title']/a")).click();

        WebElement commentDeleteButton = driver.findElement(By.xpath(
                "//div[@class='comment']" +
                        "//button[" +
                        "    contains(@class, 'comment-delete-btn')" +
                        "        and " +
                        "        ../../../../div[" +
                        "            @class='comment-content' " +
                        "                and contains(text(), '" + commentText + "')]" +
                        "        ]"));
        commentDeleteButton.click();

        // Accept confirmation of comment deleting
        driver.switchTo().alert().accept();

        assertTrue(driver.getCurrentUrl().contains(getAbsolutePath("/post/")));

        // Should throw NoSuchElementException because such comment have been deleted
        driver.findElement(By.xpath("div[@class='comment-content' and contains(text(), '" + commentText + "')]"));
    }

    @Test(priority = 2)
    public void testHeaderLinks() throws Exception {
        driver.get(getAbsolutePath("/home"));

        List<WebElement> headerImages = driver.findElements(By.className("header_image"));

        assertTrue(headerImages.size() == 3);

        for (WebElement image : headerImages) {
            String imageSrc = image.getAttribute("src");
            assertTrue(
                    imageSrc.contains("header_logo.png")
                            || imageSrc.contains("header_mail.png")
                            || imageSrc.contains("header_logout.png"));
        }
    }

    @Test(priority = 2)
    public void testMailboxLink() throws Exception {
        driver.get(getAbsolutePath("/home"));

        // Click on link to mailbox
        driver.findElement(By.xpath("//img[contains(@src, 'header_mail.png')]")).click();

        assertTrue(driver.getCurrentUrl().contains(getAbsolutePath("/mail/in")));
    }

    @Test(priority = 2)
    public void testMessageLink() throws Exception {
        driver.get(getAbsolutePath("/mail/in"));

        driver.findElement(By.xpath("//a[contains(@href, '/mail/message/')]")).click();

        assertTrue(driver.getCurrentUrl().contains("/mail/message/"));

        driver.findElement(By.xpath("//form[@id='newMessage']"));
        driver.findElement(By.xpath("//input[@id='receiver.name']"));
        driver.findElement(By.xpath("//input[@id='subject']"));
        driver.findElement(By.xpath("//textarea[@id='text']"));
        driver.findElement(By.xpath("//input[@type='submit']"));
    }

    @Test(priority = 2)
    public void testAnswerForMessage() throws Exception {
        driver.get(getAbsolutePath("/mail/in"));

        driver.findElement(By.xpath("//a[contains(@href, '/mail/message/')]")).click();

        assertTrue(driver.getCurrentUrl().contains("/mail/message/"));

        WebElement subjectField = driver.findElement(By.xpath("//input[@id='subject']"));
        WebElement textField = driver.findElement(By.xpath("//textarea[@id='text']"));
        WebElement submitButton = driver.findElement(By.xpath("//input[@type='submit']"));

        subjectField.sendKeys(messageSubject);
        textField.sendKeys(messageText);
        submitButton.submit();

        // Go to newly sent message
        driver.findElement(By.xpath(
                "//a[" +
                        "    contains(@href, '/mail/message/')" +
                        "    and" +
                        "        contains(./span/text(), '" + messageSubject + "')" +
                        "    ]"))
                .click();

        WebElement messageSender = driver.findElement(By.xpath("//div[contains(@class, 'message-info')]/a"));
        WebElement subject = driver.findElement(By.xpath("//div[contains(@class, 'message-subject')]"));
        WebElement text = driver.findElement(By.xpath("//div[contains(@class, 'message-text')]"));

        assertEquals(messageSender.getText(), "user_2");
        assertEquals(subject.getText(), messageSubject);
        assertEquals(text.getText(), messageText);
    }
}
