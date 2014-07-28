package com.my.dao;

import com.my.dao.interfaces.CommentDao;
import com.my.model.Comment;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public class CommentDaoImpl extends HibernateTemplate implements CommentDao {

    @Override
    @Transactional(readOnly = false)
    public long addComment(Comment comment) {
        comment.setPostDate(new Date());
        return (Long) save(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public Comment getComment(long id) {
        return get(Comment.class, id);
    }

}
