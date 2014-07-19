package com.my.dao;

import com.my.dao.interfaces.UserDao;
import com.my.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.DigestUtils;

import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-spring-config.xml")
public class UserDaoTest {

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
    public void testCorrectIdReturned() throws Exception {
        long returnedId = userDao.addUser(getStandardUser());
        long receivedId = userDao.getUser(returnedId).getId();

        Assert.assertEquals(returnedId, receivedId);
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

        Assert.assertEquals(readUser.getPass(),
                DigestUtils.md5DigestAsHex(savedPass.getBytes()));
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

}
