package com.my.web;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class UserWebTest extends AbstractWebTest {

    private static final String loginPage_UsernameField = "//input[@name='j_username']";
    private static final String loginPage_PasswordField = "//input[@name='j_password']";
    private static final String loginPage_RememberMeCheckbox = "//input[@name='_spring_security_remember_me']";
    private static final String loginPage_SubmitFormButton = "//div[contains(@class, 'form-unit')]/input[@type='submit']";
    private static final String user_username = "user_2";
    private static final String user_password = "user_2";
    private static final String post_votingIcon = "//div[@class='post-voting']/img[@class='post-vote']";
    private static final String home_postTitleLink = "//div[@class='post-title']/a";
    private static final String post_comment_votingIcons = "//div[@class='comment-voting']/img[@class='comment-vote']";
    private static final String post_newCommentForm = "//form[@id='newComment']";
    private static final String post_newCommentForm_TextArea = "//form[@id='newComment']//textarea[@id='text']";
    private static final String post_newCommentForm_SubmitButton = "//form[@id='newComment']//input[@type='submit']";
    private static final String header_mailIcon = "//img[contains(@src, 'header_mail.png')]";
    private static final String mailbox_messageLink = "//a[contains(@href, '/mail/message/')]";
    private static final String newMessageForm = "//form[@id='newMessage']";
    private static final String newMessageForm_receiverName = "//input[@id='receiver.name']";
    private static final String newMessageForm_subjectField = "//input[@id='subject']";
    private static final String newMessageForm_textField = "//textarea[@id='text']";
    private static final String newMessageForm_submitButton = "//input[@type='submit']";
    private static final String mailbox_message_senderLink = "//div[contains(@class, 'message-info')]/a";
    private static final String mailbox_message_subject = "//div[contains(@class, 'message-subject')]";
    private static final String mailbox_message_text = "//div[contains(@class, 'message-text')]";
    private final String commentText = "Test comment text" + RandomStringUtils.randomNumeric(10);
    private final String messageSubject = "TEST MESSAGE SUBJECT" + RandomStringUtils.randomNumeric(10);
    private final String messageText = "TEST MESSAGE TEXT" + RandomStringUtils.randomNumeric(10);

    @BeforeClass
    @Override
    public void loginBeforeClass() throws Exception {
        loginInternal(user_username, user_password, true);
    }

    @Test
    public void testPostVotingIconsForUser() throws Exception {
        driver.get(getAbsolutePath("/home"));

        List<WebElement> elementList = driver.findElements(By.xpath(post_votingIcon));

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

    @Test
    public void testCommentVotingIconsForUser() throws Exception {
        driver.get(getAbsolutePath("/home"));

        // Click link to post page
        driver.findElement(By.xpath(home_postTitleLink)).click();

        List<WebElement> elementList = driver.findElements(By.xpath(post_comment_votingIcons));

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

    @Test
    public void testNewCommentFormExists() throws Exception {
        driver.get(getAbsolutePath("/home"));

        driver.findElement(By.xpath(home_postTitleLink)).click();

        assertTrue(driver.getCurrentUrl().contains(getAbsolutePath("/post")));

        driver.findElement(By.xpath(post_newCommentForm));
        driver.findElement(By.xpath(post_newCommentForm_TextArea));
        driver.findElement(By.xpath(post_newCommentForm_SubmitButton));
    }

    @Test
    public void testNewCommentAdding() throws Exception {
        driver.get(getAbsolutePath("/home"));

        driver.findElement(By.xpath(home_postTitleLink)).click();

        assertTrue(driver.getCurrentUrl().contains(getAbsolutePath("/post/")));

        WebElement newCommentText = driver.findElement(By.xpath(post_newCommentForm_TextArea));
        WebElement newCommentSubmit = driver.findElement(By.xpath(post_newCommentForm_SubmitButton));

        newCommentText.sendKeys(commentText);
        newCommentSubmit.submit();

        assertTrue(driver.getCurrentUrl().contains(getAbsolutePath("/post/")));

        // Newly added comment
        driver.findElement(By.xpath(
                "//div[@class='comment']//div[@class='comment-content' and contains(text(), '" + commentText + "')]"));
    }

    @Test(dependsOnMethods = "testNewCommentAdding")
    public void testOwnCommentDeleting() throws Exception {
        driver.get(getAbsolutePath("/home"));

        driver.findElement(By.xpath(home_postTitleLink)).click();

        // Button for deleting comment added in previous test
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

        boolean commentNotFound = false;
        try {
            // Should throw NoSuchElementException because such comment have been deleted
            driver.findElement(By.xpath("div[@class='comment-content' and contains(text(), '" + commentText + "')]"));
        } catch (NoSuchElementException e1) {
            commentNotFound = true;
        }
        if (!commentNotFound) {
            assertTrue(false, "Comment for deleting was found after deleting");
        }
    }

    @Test
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

    @Test
    public void testMailboxLink() throws Exception {
        driver.get(getAbsolutePath("/home"));

        // Click on link to mailbox
        driver.findElement(By.xpath(header_mailIcon)).click();

        assertTrue(driver.getCurrentUrl().contains(getAbsolutePath("/mail/in")));
    }

    @Test
    public void testMessageLink() throws Exception {
        driver.get(getAbsolutePath("/mail/in"));

        driver.findElement(By.xpath(mailbox_messageLink)).click();

        assertTrue(driver.getCurrentUrl().contains("/mail/message/"));

        driver.findElement(By.xpath(newMessageForm));
        driver.findElement(By.xpath(newMessageForm_receiverName));
        driver.findElement(By.xpath(newMessageForm_subjectField));
        driver.findElement(By.xpath(newMessageForm_textField));
        driver.findElement(By.xpath(newMessageForm_submitButton));
    }

    @Test
    public void testAnswerForMessage() throws Exception {
        driver.get(getAbsolutePath("/mail/in"));

        driver.findElement(By.xpath(mailbox_messageLink)).click();

        assertTrue(driver.getCurrentUrl().contains("/mail/message/"));

        WebElement subjectField = driver.findElement(By.xpath(newMessageForm_subjectField));
        WebElement textField = driver.findElement(By.xpath(newMessageForm_textField));
        WebElement submitButton = driver.findElement(By.xpath(newMessageForm_submitButton));

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

        WebElement messageSender = driver.findElement(By.xpath(mailbox_message_senderLink));
        WebElement subject = driver.findElement(By.xpath(mailbox_message_subject));
        WebElement text = driver.findElement(By.xpath(mailbox_message_text));

        assertEquals(messageSender.getText(), "user_2");
        assertEquals(subject.getText(), messageSubject);
        assertEquals(text.getText(), messageText);
    }

    @Test(dependsOnMethods = "testAnswerForMessage")
    public void testDeleteSentMessage() throws Exception {
        driver.get(getAbsolutePath("/mail/out"));

        // Click button for deleting message sent in previous test
        driver.findElement(By.xpath(
                "//div[" +
                        "    contains(@class, 'mailbox-line')" +
                        "    and contains(./div/a/span/text(), '" + messageSubject + "')" +
                        "]/div/button[" +
                        "    contains(@class, 'delete-message-btn')" +
                        "]")).click();

//        driver.get(getAbsolutePath("/mail/out"));
        driver.navigate().refresh();

        boolean messageNotFound = false;
        try {
            // Should throw NoSuchElementException because such message have been deleted
            driver.findElement(By.xpath(
                    "//a[" +
                            "    contains(@href, '/mail/message/')" +
                            "    and" +
                            "        contains(./span/text(), '" + messageSubject + "')" +
                            "    ]"));
        } catch (NoSuchElementException e1) {
            messageNotFound = true;
        }
        if (!messageNotFound) {
            assertTrue(false, "Message for deleting was found after deleting");
        }
    }
}
