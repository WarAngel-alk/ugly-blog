package com.my.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "post")
public class Post implements Serializable {

    @Column(name = "post_id", length = 11)
    @GeneratedValue
    @Id
    private long id;

    @Column(name = "postDate", unique = false, nullable = false)
    private Date postDate;

    @Column(name = "title", unique = false, nullable = false)
    private String title;

    @Column(name = "text", nullable = false, unique = false)
    private String text;

    @ManyToMany(mappedBy = "posts")
    private List<Tag> tags;

    @OneToMany(mappedBy = "post")
    private List<UserPostMark> marks;

    @OneToMany
    private List<Comment> comments;

    // TODO: fill query!
//    @Formula("")
//    private int positiveMarks;

    // TODO: fill query!
//    @Formula("")
//    private int negativeMarks;

    public int getRating() {
        int rating = 0;
        for (UserPostMark mark : marks) {
            if (mark.isMark()) ++rating;
            else --rating;
        }

        return rating;
    }

    public int getPositiveMarks() {
        int marks = 0;
        for (UserPostMark mark : this.marks) {
            if (mark.isMark()) ++marks;
        }

        return marks;
    }

    public int getNegativeMarks() {
        int marks = 0;
        for (UserPostMark mark : this.marks) {
            if (!mark.isMark()) ++marks;
        }

        return marks;
    }

    public boolean isUserVoted(User user) {
        for (UserPostMark mark : marks) {
            if (mark.getUser().getId() == user.getId())
                return true;
        }
        return false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<UserPostMark> getMarks() {
        return marks;
    }

    public void setMarks(List<UserPostMark> marks) {
        this.marks = marks;
    }

}
