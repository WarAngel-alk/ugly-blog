package com.my.web;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

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

}
