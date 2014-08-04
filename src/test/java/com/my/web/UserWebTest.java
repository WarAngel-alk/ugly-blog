package com.my.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class UserWebTest extends AbstractWebTest {

    @Test
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

}
