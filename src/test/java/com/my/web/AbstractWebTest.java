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

    protected static final String user_password = "user_2";
    protected static final String user_username = "user_2";

    protected static final String admin_username = "user_1";
    protected static final String admin_password = "user_1";

    /* XPath queries constants */

    protected static final String header_mailIcon = "//img[contains(@src, 'header_mail.png')]";
    protected static final String home_postTitleLink = "//div[@class='post-title']/a";

    protected static final String loginPage_UsernameField = "//input[@name='j_username']";
    protected static final String loginPage_PasswordField = "//input[@name='j_password']";
    protected static final String loginPage_RememberMeCheckbox = "//input[@name='_spring_security_remember_me']";
    protected static final String loginPage_SubmitFormButton = "//div[contains(@class, 'form-unit')]/input[@type='submit']";

    protected static final String post_title = "//div[@class='post-title']";
    protected static final String post_text = "//div[@class='post-content']";
    protected static final String post_tagsString = "//div[contains(@class, 'post-tags')]/div[contains(@class, 'post-tag')]";
    protected static final String post_votingIcon = "//div[@class='post-voting']/img[@class='post-vote']";

    protected static final String post_comment_votingIcons = "//div[@class='comment-voting']/img[@class='comment-vote']";

    protected static final String post_newCommentForm = "//form[@id='newComment']";
    protected static final String post_newCommentForm_TextArea = "//form[@id='newComment']//textarea[@id='text']";
    protected static final String post_newCommentForm_SubmitButton = "//form[@id='newComment']//input[@type='submit']";

    protected static final String mailbox_messageLink = "//a[contains(@href, '/mail/message/')]";

    protected static final String mailbox_message_senderLink = "//div[contains(@class, 'message-info')]/a";
    protected static final String mailbox_message_subject = "//div[contains(@class, 'message-subject')]";
    protected static final String mailbox_message_text = "//div[contains(@class, 'message-text')]";

    protected static final String newMessageForm = "//form[@id='newMessage']";
    protected static final String newMessageForm_receiverName = "//input[@id='receiver.name']";
    protected static final String newMessageForm_subjectField = "//input[@id='subject']";
    protected static final String newMessageForm_textField = "//textarea[@id='text']";
    protected static final String newMessageForm_submitButton = "//input[@type='submit']";

    protected static final String addPostForm_TitleField = "//form[@id='post']//input[@id='title']";
    protected static final String addPostForm_TextField = "//form[@id='post']//textarea[@id='text']";
    protected static final String addPostForm_TagsField = "//form[@id='post']//input[@name='tagsString']";
    protected static final String addPostForm_SubmitButton = "//form[@id='post']//input[@type='submit']";

    /* End of XPath queries constants */

    /* XPath generation methods */

    protected String commentContentByText(String text) {
        return "//div[@class='comment']" +
                "   //div[" +
                "       @class='comment-content' " +
                "       and contains(text(), '" + text + "')" +
                "    ]";
    }

    protected String messageLinkBySubject(String subject) {
        return "//a[" +
                "    contains(@href, '/mail/message/')" +
                "    and" +
                "        contains(./span/text(), '" + subject + "')" +
                "    ]";
    }

    protected String commentDeleteButtonByCommentText(String text) {
        return "//div[@class='comment']" +
                "//button[" +
                "    contains(@class, 'comment-delete-btn')" +
                "        and " +
                "        ../../../../div[" +
                "            @class='comment-content' " +
                "                and contains(text(), '" + text + "')]" +
                "        ]";
    }

    protected String messageDeleteButtonByMessageSubject(String subject) {
        return "//div[" +
                "    contains(@class, 'mailbox-line')" +
                "    and contains(./div/a/span/text(), '" + subject + "')" +
                "]/div/button[" +
                "    contains(@class, 'delete-message-btn')" +
                "]";
    }

    /* End of XPath generation methods */

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

    protected void logoutInternal() {
        driver.get(getAbsolutePath("/logout"));
    }
}
