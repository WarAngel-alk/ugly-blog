package com.my.web;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AdminWebTest extends AbstractWebTest {
    
    private static final String testPostTitle = "TEST POST TITLE " + RandomStringUtils.randomNumeric(10);
    private static final String testPostText = "TEST POST TEXT " + RandomStringUtils.randomNumeric(10);
    private static final String testPostTags = "tag1, tag2";

    @BeforeClass
    @Override
    public void loginBeforeClass() throws Exception {
        loginInternal(admin_username, admin_password, true);
    }

    @AfterClass
    @Override
    public void logoutAfterClass() throws Exception {
        logoutInternal();
    }

    @Test
    public void testAddPost() throws Exception {
        driver.get(getAbsolutePath("/post/add"));

        assertTrue(driver.getCurrentUrl().contains(getAbsolutePath("/post/add")));

        WebElement titleField = driver.findElement(By.xpath(addPostForm_TitleField));
        WebElement textField = driver.findElement(By.xpath(addPostForm_TextField));
        WebElement tagsField = driver.findElement(By.xpath(addPostForm_TagsField));
        WebElement submitButton = driver.findElement(By.xpath(addPostForm_SubmitButton));
        
        titleField.sendKeys(testPostTitle);
        textField.sendKeys(testPostText);
        tagsField.sendKeys(testPostTags);
        submitButton.submit();

        assertTrue(driver.getCurrentUrl().contains(getAbsolutePath("/post/")));

        WebElement postTitle = driver.findElement(By.xpath(post_title));
        WebElement postText = driver.findElement(By.xpath(post_text));
        List<WebElement> tags = driver.findElements(By.xpath(post_tagsString));

        assertTrue(postTitle.getText().contains(testPostTitle));
        assertTrue(postText.getText().contains(testPostText));

        List<String> testTagsList = Arrays.asList(testPostTags.split(", "));

        assertEquals(tags.size(), testTagsList.size());
        for (WebElement tag : tags) {
            assertTrue(testTagsList.contains(tag.getText().trim()));
        }
    }
}
