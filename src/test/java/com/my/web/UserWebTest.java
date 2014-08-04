package com.my.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

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

    @Test(dependsOnMethods = "testUserLogin")
    public void testVotingIconsForUser() throws Exception {
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

}
