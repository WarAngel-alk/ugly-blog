package com.my.dao;

import com.my.dao.interfaces.PostDao;
import com.my.model.Post;

import java.util.Arrays;
import java.util.List;

public class PostDaoImpl implements PostDao {

    @Override
    public List<Post> getRecentPosts() {
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
