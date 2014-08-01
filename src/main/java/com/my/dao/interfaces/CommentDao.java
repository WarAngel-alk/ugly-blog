package com.my.dao.interfaces;

import com.my.model.Comment;

public interface CommentDao {

    public long addComment(Comment comment);

    public Comment getComment(long id);

    public void deleteComment(Comment comment);
}
