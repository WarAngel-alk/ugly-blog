package com.my.web;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class UserWebTest extends AbstractWebTest {

    private final String commentText = "Test comment text" + RandomStringUtils.randomNumeric(10);
    private final String messageSubject = "TEST MESSAGE SUBJECT" + RandomStringUtils.randomNumeric(10);
    private final String messageText = "TEST MESSAGE TEXT" + RandomStringUtils.randomNumeric(10);

    @BeforeClass
    @Override
    public void loginBeforeClass() throws Exception {
        loginInternal(user_username, user_password, true);
    }

    @AfterClass
    @Override
    public void logoutAfterClass() throws Exception {
        logoutInternal();
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
        driver.findElement(By.xpath(commentContentByText(commentText)));
    }

    @Test(dependsOnMethods = "testNewCommentAdding")
    public void testOwnCommentDeleting() throws Exception {
        driver.get(getAbsolutePath("/home"));

        driver.findElement(By.xpath(home_postTitleLink)).click();

        WebElement commentDeleteButton = driver.findElement(By.xpath(commentDeleteButtonByCommentText(commentText)));
        commentDeleteButton.click();

        // Accept confirmation of comment deleting
        driver.switchTo().alert().accept();

        driver.navigate().refresh();
        assertTrue(driver.getCurrentUrl().contains(getAbsolutePath("/post/")));

        boolean deletedCommentNotFound = reloadUntilFound(By.xpath(postLinkByPostTitle(commentContentByText(commentText))), 2000);
        assertTrue(deletedCommentNotFound);
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
        driver.findElement(By.xpath(messageLinkBySubject(messageSubject))).click();

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
        driver.findElement(By.xpath(messageDeleteButtonByMessageSubject(messageSubject))).click();

        driver.navigate().refresh();

        boolean deletedMessageNotFound = reloadUntilFound(By.xpath(postLinkByPostTitle(messageLinkBySubject(messageSubject))), 2000);
        assertTrue(deletedMessageNotFound);
    }
}
