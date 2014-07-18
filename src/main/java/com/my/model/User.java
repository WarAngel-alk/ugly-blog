package com.my.model;

import java.util.Date;
import java.util.List;

public class User {

    private long id;

    private String name;

    private String passHash;

    private Date registrationDate;

    private String email;

    private String avatarPath;

    private List<Post> votedPosts;

    private List<Comment> votedComments;

    private List<Comment> hisComments;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassHash() {
        return passHash;
    }

    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public List<Post> getVotedPosts() {
        return votedPosts;
    }

    public void setVotedPosts(List<Post> votedPosts) {
        this.votedPosts = votedPosts;
    }

    public List<Comment> getVotedComments() {
        return votedComments;
    }

    public void setVotedComments(List<Comment> votedComments) {
        this.votedComments = votedComments;
    }

    public List<Comment> getHisComments() {
        return hisComments;
    }

    public void setHisComments(List<Comment> hisComments) {
        this.hisComments = hisComments;
    }

}
