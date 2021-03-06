package com.my.dao;

import com.my.dao.interfaces.UserDao;
import com.my.model.User;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public class UserDaoImpl extends HibernateTemplate implements UserDao {

    @Override
    @Transactional(readOnly = false)
    public long addUser(User user) {
        user.setRegistrationDate(new Date());
        // Replace password with it's bcrypt hash
        user.setPass(new BCryptPasswordEncoder().encode(user.getPass()));

        long user_id = (Long) save(user);

        // Add role 'ROLE_USER' for new user
        executeWithNativeSession(session ->
                session.createSQLQuery(
                        "INSERT INTO `user_role` " +
                                "(`user_id`, `role_id`) " +
                                "VALUES " +
                                "(:user_id, (" +
                                "select id from `role` where `role` = 'ROLE_USER'" +
                                "))")
                        .setParameter("user_id", user_id)
                        .executeUpdate());

        return user_id;
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(long id) {
        return get(User.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(String username) {
        return ((List<User>) find("from User where name = ?", username)).get(0);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateUser(User user) {
        merge(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isUsernameFree(String username) {
        Long usersWithSaveName = (Long) executeWithNativeSession(session ->
                session.createQuery("select count(*) from User where name = :username")
                        .setParameter("username", username)
                        .list()
                        .get(0));

        return usersWithSaveName == 0;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isEmailFree(String email) {
        Long usersWithSaveEmail = (Long) executeWithNativeSession(session ->
                session.createQuery("select count(*) from User where email = :email")
                        .setParameter("email", email)
                        .list()
                        .get(0));

        return usersWithSaveEmail == 0;
    }
}
