package com.my.dao.interfaces;

import com.my.model.UserCommentMark;
import com.my.model.UserPostMark;

public interface MarkDao {

    public void addPostMark(UserPostMark mark);

    public void addCommentMark(UserCommentMark mark);

}
