package com.my.dao;

import com.my.dao.interfaces.PostDao;
import com.my.model.Post;
import org.springframework.orm.hibernate4.HibernateTemplate;

import java.util.ArrayList;
import java.util.List;

public class PostDaoImpl extends HibernateTemplate implements PostDao {

    private static final int DEFAULT_POSTS_PER_PAGE = 10;

    private static final List<Post> EMPTY_POST_LIST = new ArrayList<>();

    @Override
    public List<Post> getPosts() {
        return EMPTY_POST_LIST;
    }

    @Override
    public List<Post> getPostsForPage(int page) {
        return getPostsForPage(page, DEFAULT_POSTS_PER_PAGE);
    }

    @Override
    public List<Post> getPostsForPage(int page, int postsPerPage) {
        return EMPTY_POST_LIST;
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
