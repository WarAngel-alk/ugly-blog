package com.my.dao;

import com.my.dao.interfaces.UserDao;
import com.my.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.DigestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-spring-config.xml")
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    private String STANDARD_NAME;
    private String STANDARD_PASS;
    private String STANDARD_EMAIL;
    private String STANDARD_PASS_HASH;

    @Before
    public void setUp() throws Exception {
        STANDARD_NAME = "user_1";
        STANDARD_PASS = "pass_1";
        STANDARD_EMAIL = "email_1";
        STANDARD_PASS_HASH = DigestUtils.md5DigestAsHex(STANDARD_PASS.getBytes());
    }

    private User getStandardUser() {
        User user = new User();
        user.setName(STANDARD_NAME);
        user.setPass(STANDARD_PASS);
        user.setEmail(STANDARD_EMAIL);

        return user;
    }

    @Test
    public void testCorrectIdReturned() throws Exception {
        long returnedId = userDao.addUser(getStandardUser());
        long receivedId = userDao.getUser(returnedId).getId();

        Assert.assertEquals(returnedId, receivedId);
    }

    @Test
    public void testUserAddAndGet() throws Exception {
        long id = userDao.addUser(getStandardUser());

        User user = userDao.getUser(id);

        Assert.assertEquals(user.getName(), STANDARD_NAME);
        Assert.assertEquals(user.getEmail(), STANDARD_EMAIL);
    }

    @Test
    public void testPasswordHashGenerating() throws Exception {
        long returnedId = userDao.addUser(getStandardUser());
        User user = userDao.getUser(returnedId);

        Assert.assertEquals(user.getPass(), STANDARD_PASS_HASH);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    
}
