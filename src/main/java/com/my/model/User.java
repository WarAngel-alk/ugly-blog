package com.my.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
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

    @Column(name = "name", nullable = false, unique = true, length = 50)
    @Length(max = 50, message = "Name should not be longer than 255 characters")
    @Pattern(regexp = "[\\w\\d][\\w\\d\\\\._?,-]*[\\w\\d]", message =
            "Name should starts and ends with alphanumeric character and contains only alphanumeric and ? , . _ -")
    @NotEmpty(message = "Name may not be empty")
    private String name;

    @Column(name = "pass", nullable = false, unique = false, length = 60)
    @NotEmpty(message = "Password may not be empty")
    private String pass;

    @Column(name = "regDate", nullable = false, unique = false)
    private Date registrationDate;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    @Email(message = "Invalid email address")
    @NotEmpty(message = "Email may not be empty")
    private String email;

    @Column(name = "avatarPath", nullable = true, unique = false)
    @URL(message = "Invalid avatar URL")
    private String avatarPath;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserPostMark> postMarks;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserCommentMark> commentMarks;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Comment> hisComments;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<Message> incomingMessages;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id == user.id;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (pass != null ? pass.hashCode() : 0);
        result = 31 * result + (registrationDate != null ? registrationDate.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("User");
        sb.append("{id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", pass='").append(pass).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", registrationDate=").append(registrationDate);
        sb.append(", avatarPath='").append(avatarPath).append('\'');
        sb.append('}');
        return sb.toString();
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
