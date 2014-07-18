package com.my.model;

import javax.persistence.*;
import java.util.List;

@Entity()
@Table(name = "tag")
public class Tag {

    @Column(name = "tag_id", length = 11)
    @GeneratedValue
    @Id
    private long id;

    @Column(name = "name", length = 100)
    private String name;

    @ManyToMany
    private List<Post> posts;

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

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

}