package com.my.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements Serializable, DomainObject {

    @Column(name = "user_id")
    @GeneratedValue
    @Id
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "pass", nullable = false, unique = false)
    private String pass;

    @Column(name = "regDate", nullable = false, unique = false)
    private Date registrationDate;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "avatarPath", nullable = true, unique = false)
    private String avatarPath;

    @OneToMany(mappedBy = "user")
    private List<UserPostMark> postMarks;

    @OneToMany(mappedBy = "user")
    private List<UserCommentMark> commentMarks;

    @OneToMany(mappedBy = "author")
    private List<Comment> hisComments;

    @OneToMany(mappedBy = "receiver")
    private List<Message> incomingMessages;

    @OneToMany(mappedBy = "sender")
    private List<Message> outcomingMessages;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    public User(String name, String pass, String email) {
        this.name = name;
        this.pass = pass;
        this.email = email;
    }

    public User(String name, String pass, String email, String avatarPath) {
        this.name = name;
        this.pass = pass;
        this.email = email;
        this.avatarPath = avatarPath;
    }

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

    public String getPass() {
        return pass;
    }

    public void setPass(String passHash) {
        this.pass = passHash;
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

    public List<UserPostMark> getPostMarks() {
        return postMarks;
    }

    public void setPostMarks(List<UserPostMark> marks) {
        this.postMarks = marks;
    }

    public List<UserCommentMark> getCommentMarks() {
        return commentMarks;
    }

    public void setCommentMarks(List<UserCommentMark> commentMarks) {
        this.commentMarks = commentMarks;
    }

    public List<Comment> getHisComments() {
        return hisComments;
    }

    public void setHisComments(List<Comment> hisComments) {
        this.hisComments = hisComments;
    }

    public List<Message> getIncomingMessages() {
        return incomingMessages;
    }

    public void setIncomingMessages(List<Message> incomingMessages) {
        this.incomingMessages = incomingMessages;
    }

    public List<Message> getOutcomingMessages() {
        return outcomingMessages;
    }

    public void setOutcomingMessages(List<Message> outcomingMessages) {
        this.outcomingMessages = outcomingMessages;
    }

}
