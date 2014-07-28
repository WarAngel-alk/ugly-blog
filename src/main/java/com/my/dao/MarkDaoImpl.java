package com.my.dao;

import com.my.dao.interfaces.MarkDao;
import com.my.model.UserCommentMark;
import com.my.model.UserPostMark;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

public class MarkDaoImpl extends HibernateTemplate implements MarkDao {

    @Override
    @Transactional(readOnly = false)
    public void addPostMark(UserPostMark mark) {
        save(mark);

        mark.getUser().getPostMarks().add(mark);
        mark.getPost().getMarks().add(mark);

        save(mark.getUser());
        save(mark.getPost());
    }

    @Override
    @Transactional(readOnly = false)
    public void addCommentMark(UserCommentMark mark) {
        save(mark);

        mark.getUser().getCommentMarks().add(mark);
        mark.getComment().getMarks().add(mark);

        save(mark.getUser());
        save(mark.getComment());
    }

}
