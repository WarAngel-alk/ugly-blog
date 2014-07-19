package com.my.dao;

import com.my.dao.interfaces.UserDao;
import com.my.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.util.DigestUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Random;

@ContextConfiguration(locations = {"classpath:test-spring-config.xml"})
public class UserDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserDao userDao;

    private User getStandardUser() {
        Random random = new Random();
        User user = new User();
        user.setName("user_" + random.nextInt());
        user.setPass("pass_" + random.nextInt());
        user.setEmail("email_" + random.nextInt());

        return user;
    }

    @Test
    public void testIdReturned() throws Exception {
        long returnedId = userDao.addUser(getStandardUser());
        long receivedId = userDao.getUser(returnedId).getId();

        Assert.assertEquals(receivedId, returnedId);
    }

    @Test
    public void testUserAddAndGet() throws Exception {
        User savedUser = getStandardUser();
        long id = userDao.addUser(savedUser);

        User readUser = userDao.getUser(id);

        Assert.assertEquals(readUser.getName(), savedUser.getName());
        Assert.assertEquals(readUser.getEmail(), savedUser.getEmail());
    }

    @Test
    public void testPasswordHashGenerating() throws Exception {
        User savedUser = getStandardUser();
        String savedPass = savedUser.getPass();
        long returnedId = userDao.addUser(savedUser);
        User readUser = userDao.getUser(returnedId);

        Assert.assertEquals(DigestUtils.md5DigestAsHex(savedPass.getBytes()), readUser.getPass());
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

}
