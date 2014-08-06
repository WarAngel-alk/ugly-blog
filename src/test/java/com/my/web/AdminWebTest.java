package com.my.web;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class AdminWebTest extends AbstractWebTest {

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
