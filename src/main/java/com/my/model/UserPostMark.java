package com.my.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_post_mark")
public class UserPostMark implements Serializable, DomainObject {

    @ManyToOne(cascade = CascadeType.ALL)
    @Id
    private Post post;

    @ManyToOne(cascade = CascadeType.ALL)
    @Id
    private User user;

    @Column(name = "mark", nullable = false, unique = false)
    private boolean mark;

    public UserPostMark() {
    }

    public UserPostMark(User user, Post post, boolean mark) {
        this.user = user;
        this.post = post;
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPostMark otherMark = (UserPostMark) o;

        return user.equals(otherMark.user)
                && post.equals(otherMark.post);
    }

    @Override
    public int hashCode() {
        int result = post.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + (mark ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("UserPostMark");
        sb.append("{postId=").append(post.getId());
        sb.append(", userId=").append(user.getId());
        sb.append(", mark=").append(mark ? "positive" : "negative");
        sb.append('}');
        return sb.toString();
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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
