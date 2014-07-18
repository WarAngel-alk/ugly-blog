package com.my.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_post_mark")
public class UserPostMark implements Serializable {

    @ManyToOne
    @Id
    private Post post;

    @ManyToOne
    @Id
    private User user;

    @Column(name = "mark", nullable = false, unique = false)
    private boolean mark;

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
