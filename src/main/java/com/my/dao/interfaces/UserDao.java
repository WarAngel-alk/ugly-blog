package com.my.dao.interfaces;

import com.my.model.User;

public interface UserDao {

    public long addUser(User user);

    public User getUser(long id);

}
