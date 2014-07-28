package com.my.dao.interfaces;

import com.my.model.Comment;

public interface CommentDao {

    public long addComment(Comment comment);

    Comment getComment(long id);
}
