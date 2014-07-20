package com.my.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "message")
public class Message implements Serializable, DomainObject {

    @Column(name = "message_id")
    @GeneratedValue
    @Id
    private long id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    @Column(name = "date", nullable = false, unique = false)
    private Date date;

    @Column(name = "subject", nullable = true, unique = false)
    private String subject;

    @Column(name = "text", nullable = false, unique = false)
    private String text;

    @Column(name = "isRead", nullable = false, unique = false)
    private boolean isRead;

    @Column(name = "deletedBySender", nullable = false, unique = false)
    private boolean deletedBySender;

    @Column(name = "deletedByReceiver", nullable = false, unique = false)
    private boolean deletedByReceiver;

    public Message() {
    }

    public Message(String subject) {
        this.subject = subject;
    }

    public Message(String subject, String text) {
        this.subject = subject;
        this.text = text;
    }

    public Message(User sender, User receiver, String text) {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
    }

    public Message(User sender, User receiver, String subject, String text) {
        this.subject = subject;
        this.text = text;
        this.sender = sender;
        this.receiver = receiver;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isDeletedBySender() {
        return deletedBySender;
    }

    public void setDeletedBySender(boolean deletedBySender) {
        this.deletedBySender = deletedBySender;
    }

    public boolean isDeletedByReceiver() {
        return deletedByReceiver;
    }

    public void setDeletedByReceiver(boolean deletedByReceiver) {
        this.deletedByReceiver = deletedByReceiver;
    }

}
