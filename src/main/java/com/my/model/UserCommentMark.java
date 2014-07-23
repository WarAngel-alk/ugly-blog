package com.my.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_comment_mark")
public class UserCommentMark implements Serializable, DomainObject {

    @ManyToOne(cascade = CascadeType.ALL)
    @Id
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(cascade = CascadeType.ALL)
    @Id
    @JoinColumn(name = "user_id")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserCommentMark otherMark = (UserCommentMark) o;

        return user.equals(otherMark.user)
                && comment.equals(otherMark.comment);
    }

    @Override
    public int hashCode() {
        int result = comment.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + (mark ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("UserCommentMark");
        sb.append("{userId=").append(user.getId());
        sb.append(", commentId=").append(comment.getId());
        sb.append(", mark=").append(mark ? "positive" : "negative");
        sb.append('}');
        return sb.toString();
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
