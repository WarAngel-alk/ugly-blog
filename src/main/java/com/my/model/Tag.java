package com.my.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity()
@Table(name = "tag")
public class Tag implements Serializable, DomainObject {

    @Column(name = "tag_id", length = 11)
    @GeneratedValue
    @Id
    private long id;

    @Column(name = "name", length = 100)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Post> posts;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    public Tag(String name, List<Post> posts) {
        this.name = name;
        this.posts = posts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        return id == tag.id;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Tag");
        sb.append("{id=").append(id);
        sb.append(", name='").append(name).append('\'');
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

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

}
