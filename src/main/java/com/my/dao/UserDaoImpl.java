package com.my.dao;

import com.my.dao.interfaces.UserDao;
import com.my.model.User;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.util.DigestUtils;

import java.util.Date;

public class UserDaoImpl extends HibernateTemplate implements UserDao {

    @Override
    public long addUser(User user) {
        user.setRegistrationDate(new Date());
        // Replace password with it's md5 hash
        user.setPass(DigestUtils.md5DigestAsHex(user.getPass().getBytes()));

        return (Long) save(user);
    }

    @Override
    public User getUser(long id) {
        return get(User.class, id);
    }
}
