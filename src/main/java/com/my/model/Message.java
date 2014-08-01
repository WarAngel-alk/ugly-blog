package com.my.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

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

    @Column(name = "subject", nullable = true, unique = false, length = 255)
    @Length(min = 0, max = 255, message = "Message title should not be longer than 255 characters")
    private String subject;

    @Column(name = "text", nullable = false, unique = false, length = 10000)
    @Length(max = 10000, message = "Message for more than 10k symbols? O_O Leo Tolstoy, relogin please")
    @NotEmpty(message = "Message text may not be empty")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (id != message.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sender != null ? sender.hashCode() : 0;
        result = 31 * result + (receiver != null ? receiver.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Message");
        sb.append("{id=").append(id);
        sb.append(", senderId=").append(sender.getId());
        sb.append(", receiverId=").append(receiver.getId());
        sb.append(", date=").append(date);
        sb.append(", subject='").append(subject).append('\'');
        sb.append(", text='").append(text).append('\'');
        sb.append(", isRead=").append(isRead);
        sb.append(", deletedBySender=").append(deletedBySender);
        sb.append(", deletedByReceiver=").append(deletedByReceiver);
        sb.append('}');
        return sb.toString();
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
