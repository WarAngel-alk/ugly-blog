package com.my.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_comment_mark")
public class UserCommentMark implements Serializable, DomainObject {

    @ManyToOne
    @Id
    private Comment comment;

    @ManyToOne
    @Id
    private User user;

    @Column(name = "mark", nullable = false, unique = false)
    private boolean mark;

    public UserCommentMark() {
    }

    public UserCommentMark(User user, Comment comment, boolean mark) {
        this.user = user;
        this.comment = comment;
        this.mark = mark;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isMark() {
        return mark;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }

}
