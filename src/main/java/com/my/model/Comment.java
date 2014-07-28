package com.my.model;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "comment")
public class Comment implements Serializable, DomainObject {

    @Column(name = "comment_id")
    @GeneratedValue
    @Id
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Post post;

    @Column(name = "postDate", nullable = false, unique = false)
    private Date postDate;

    @Column(name = "text", nullable = false, unique = false, length = 5000)
    private String text;

    @ManyToOne(cascade = CascadeType.ALL)
    private User author;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<UserCommentMark> marks;

    @Formula(value =
            "(SELECT " +
                    "count(marks.mark) " +
                    "FROM user_comment_mark AS marks WHERE" +
                    " marks.comment_id = comment_id AND marks.mark = 1)")
    private int positiveMarks;

    @Formula(value =
            "(SELECT " +
                    "count(marks.mark) " +
                    "FROM user_comment_mark AS marks WHERE" +
                    " marks.comment_id = comment_id AND marks.mark = 0)")
    private int negativeMarks;

    @Formula("(SELECT " +
            "(SELECT " +
            "count(marks.mark) " +
            "FROM user_comment_mark AS marks WHERE " +
            "marks.comment_id = comment_id AND marks.mark = 1) " +
            "- " +
            "(SELECT " +
            "count(marks.mark) " +
            "FROM user_comment_mark AS marks WHERE " +
            "marks.comment_id = comment_id AND marks.mark = 0)" +
            ")")
    private int rating;

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

    public int countPositiveMarks() {
        int count = 0;
        for (UserCommentMark mark : this.marks) {
            if (mark.isMark()) count++;
        }
        return count;
    }

    public int countNegativeMarks() {
        int count = 0;
        for (UserCommentMark mark : this.marks) {
            if (!mark.isMark()) count++;
        }
        return count;
    }

    public int countRating() {
        return countPositiveMarks() - countNegativeMarks();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        return id == comment.id;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + author.hashCode();
        result = 31 * result + postDate.hashCode();
        result = 31 * result + text.hashCode();
        result = 31 * result + post.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Comment");
        sb.append("{id=").append(id);
        sb.append(", authorId=").append(author.getId());
        sb.append(", postDate=").append(postDate);
        sb.append(", text='").append(text).append('\'');
        sb.append(", postId=").append(post.getId());
        sb.append('}');
        return sb.toString();
    }


    public int getPositiveMarks() {
        return positiveMarks;
    }

    public void setPositiveMarks(int positiveMarks) {
        this.positiveMarks = positiveMarks;
    }

    public int getNegativeMarks() {
        return negativeMarks;
    }

    public void setNegativeMarks(int negativeMarks) {
        this.negativeMarks = negativeMarks;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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
