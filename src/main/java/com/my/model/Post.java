package com.my.model;

import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "post")
public class Post implements Serializable, DomainObject {

    @Column(name = "post_id", length = 11)
    @GeneratedValue
    @Id
    private long id;

    @Column(name = "postDate", unique = false, nullable = false)
    private Date postDate;

    @Column(name = "title", unique = false, nullable = false, length = 255)
    @Length(max = 255, message = "Post title should not be longer than 255 characters")
    @NotEmpty(message = "Post title may not be empty")
    private String title;

    @Column(name = "text", nullable = false, unique = false, length = 10000)
    @Length(max = 10000, message = "Max post length is 10000 symbols")
    private String text;

    @ManyToMany(mappedBy = "posts", cascade = CascadeType.ALL)
    private List<Tag> tags;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<UserPostMark> marks;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @Formula(value =
            "(SELECT " +
                    "count(marks.mark) " +
                    "FROM user_post_mark AS marks WHERE " +
                    "marks.post_id = post_id AND marks.mark = 1)")
    private int positiveMarks;

    @Formula(value =
            "(SELECT " +
                    "count(marks.mark) " +
                    "FROM user_post_mark AS marks WHERE " +
                    "marks.post_id = post_id AND marks.mark = 0)")
    private int negativeMarks;

    @Formula("(SELECT " +
            "(SELECT " +
            "count(marks.mark) " +
            "FROM user_post_mark AS marks WHERE " +
            "marks.post_id = post_id AND marks.mark = 1) " +
            "- " +
            "(SELECT " +
            "count(marks.mark) " +
            "FROM user_post_mark AS marks WHERE " +
            "marks.post_id = post_id AND marks.mark = 0)" +
            ")")
    private int rating;

    public Post() {
    }

    public Post(String title) {
        this.title = title;
    }

    public Post(String title, String text) {
        this.text = text;
        this.title = title;
    }

    public int countPositiveMarks() {
        int count = 0;
        for (UserPostMark mark : this.marks) {
            if (mark.isMark()) count++;
        }
        return count;
    }

    public int countNegativeMarks() {
        int count = 0;
        for (UserPostMark mark : this.marks) {
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

        Post post = (Post) o;

        return id == post.id;
    }

    @Override
    public int hashCode() {
        int result = postDate != null ? postDate.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Post");
        sb.append("{id=").append(id);
        sb.append(", postDate=").append(postDate);
        sb.append(", title='").append(title).append('\'');
        sb.append(", text='").append(text).append('\'');
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

    public boolean didUserVote(User user) {
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
