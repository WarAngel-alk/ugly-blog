package com.my.dao.interfaces;

import com.my.model.User;

public interface UserDao {

    public long addUser(User user);

    public User getUser(long id);

    User getUser(String username);

    void updateUser(User user);

    boolean isUsernameFree(String username);

    boolean isEmailFree(String email);
}
