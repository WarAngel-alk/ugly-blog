package com.my.dao;

import com.my.dao.interfaces.UserDao;
import com.my.model.User;

public class UserDaoImpl implements UserDao {

    @Override
    public long addUser(User user) {
        return -1L;
    }

    @Override
    public User getUser(long id) {
        return new User();
    }
}
