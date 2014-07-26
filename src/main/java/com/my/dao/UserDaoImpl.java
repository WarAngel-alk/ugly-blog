package com.my.dao;

import com.my.dao.interfaces.UserDao;
import com.my.model.User;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

public class UserDaoImpl extends HibernateTemplate implements UserDao {

    @Override
    @Transactional(readOnly = false)
    public long addUser(User user) {
        user.setRegistrationDate(new Date());
        // Replace password with it's md5 hash
        user.setPass(DigestUtils.md5DigestAsHex(user.getPass().getBytes()));

        return (Long) save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(long id) {
        return get(User.class, id);
    }

    @Override
    public User getUser(String username) {
        return ((List<User>) find("from User where name = ?", username)).get(0);
    }
}
