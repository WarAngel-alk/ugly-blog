package com.my.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "comment")
public class Comment implements Serializable {

    @Column(name = "comment_id")
    @GeneratedValue
    @Id
    private long id;

    @ManyToOne()
    private Post post;

    @Column(name = "postDate", nullable = false, unique = false)
    private Date postDate;

    @Column(name = "text", nullable = false, unique = false)
    private String text;

    @ManyToOne()
    private User author;

    @OneToMany(mappedBy = "comment")
    private List<UserCommentMark> marks;

//    // TODO: fill query!
//    @Formula("")
//    private int positiveMarks;
//
//    // TODO: fill query!
//    @Formula("")
//    private int negativeMarks;

    public Comment() {
    }

    public Comment(User author) {
        this.author = author;
    }

    public Comment(User author, Post post) {
        this.author = author;
        this.post = post;
    }

    public Comment(User author, Post post, String text) {
        this.author = author;
        this.post = post;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<UserCommentMark> getMarks() {
        return marks;
    }

    public void setMarks(List<UserCommentMark> marks) {
        this.marks = marks;
    }

}
