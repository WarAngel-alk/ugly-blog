package com.my.dao;

import com.my.dao.interfaces.CommentDao;
import com.my.model.Comment;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public class CommentDaoImpl extends HibernateTemplate implements CommentDao {

    @Transactional(readOnly = false)
    @Override
    public long addComment(Comment comment) {
        comment.setPostDate(new Date());
        return (Long) save(comment);
    }
}
