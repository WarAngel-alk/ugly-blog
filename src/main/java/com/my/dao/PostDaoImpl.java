package com.my.dao;

import com.my.dao.interfaces.PostDao;
import com.my.model.Post;
import com.my.model.Tag;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public class PostDaoImpl extends HibernateTemplate implements PostDao {

    private static final int DEFAULT_POSTS_PER_PAGE = 10;

    @Override
    @Transactional(readOnly = true)
    public List<Post> getPosts() {
        return loadAll(Post.class);
    }

    @Override
    public List<Post> getPostsForPage(int page) {
        return getPostsForPage(page, DEFAULT_POSTS_PER_PAGE);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getPostsForPage(int page, int postsPerPage) {
        return this.<List<Post>>executeWithNativeSession(
                session -> session
                        .createQuery("from Post")
                        .setFirstResult((page - 1) * postsPerPage)
                        .setMaxResults(postsPerPage)
                        .list());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getPostsByTag(Tag tag) {
        return this.<List<Post>>executeWithNativeSession(
                session -> session
                        .createQuery("from Post where :tag in elements(tags)")
                        .setParameter("tag", tag)
                        .list()
        );
    }

    @Override
    public List<Post> getPostsByTagForPage(Tag tag, int page) {
        return getPostsByTagForPage(tag, page, DEFAULT_POSTS_PER_PAGE);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getPostsByTagForPage(Tag tag, int page, int postsPerPage) {
        return this.<List<Post>>executeWithNativeSession(
                session -> session
                        .createQuery("from Post where :tag in elements(tags)")
                        .setParameter("tag", tag)
                        .setFirstResult((page - 1) * postsPerPage)
                        .setMaxResults(postsPerPage)
                        .list()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Post getPost(long id) {
        return get(Post.class, id);
    }

    @Override
    @Transactional(readOnly = false)
    public long addPost(Post post) {
        post.setPostDate(new Date());
        return (Long) save(post);
    }

    @Override
    @Transactional(readOnly = false)
    public void updatePost(Post post) {
        merge(post);
    }

    @Override
    @Transactional(readOnly = false)
    public void deletePost(Post post) {
        delete(post);
    }

}
