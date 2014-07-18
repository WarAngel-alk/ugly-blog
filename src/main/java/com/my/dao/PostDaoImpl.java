package com.my.dao;

import com.my.dao.interfaces.PostDao;
import com.my.model.Post;
import org.springframework.orm.hibernate4.HibernateTemplate;

import java.util.Arrays;
import java.util.List;

public class PostDaoImpl extends HibernateTemplate implements PostDao {

    @Override
    public List<Post> getPosts() {
        return Arrays.asList();
    }

    @Override
    public List<Post> getPostsForPage(int page) {
        return Arrays.asList();
    }

    @Override
    public Post getPost(long id) {
        return new Post();
    }

    @Override
    public long addPost(Post post) {
        return -1L;
    }

    @Override
    public void updatePost(Post post) {

    }

}
