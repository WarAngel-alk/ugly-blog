package com.my.dao;

import com.my.dao.interfaces.PostDao;
import com.my.model.Post;
import org.springframework.orm.hibernate4.HibernateTemplate;

import java.util.Date;
import java.util.List;

public class PostDaoImpl extends HibernateTemplate implements PostDao {

    private static final int DEFAULT_POSTS_PER_PAGE = 10;

    @Override
    public List<Post> getPosts() {
        return loadAll(Post.class);
    }

    @Override
    public List<Post> getPostsForPage(int page) {
        return getPostsForPage(page, DEFAULT_POSTS_PER_PAGE);
    }

    @Override
    public List<Post> getPostsForPage(int page, int postsPerPage) {
        return this.<List<Post>>executeWithNativeSession(
                session -> session
                        .createQuery("from Post")
                        .setFirstResult((page - 1) * postsPerPage)
                        .setMaxResults(postsPerPage)
                        .list());
    }

    @Override
    public Post getPost(long id) {
        return get(Post.class, id);
    }

    @Override
    public long addPost(Post post) {
        post.setPostDate(new Date());
        return (Long) save(post);
    }

    @Override
    public void updatePost(Post post) {
        merge(post);
    }

}
