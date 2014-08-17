package com.my.dao;

import com.my.dao.interfaces.PostDao;
import com.my.model.Post;
import com.my.model.Tag;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public class PostDaoImpl extends HibernateTemplate implements PostDao {

    private static final int DEFAULT_POSTS_PER_PAGE = 10;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "allPosts")
    public List<Post> getPosts() {
        return loadAll(Post.class);
    }

    @Override
    @Cacheable(value = "firstPagePosts", condition = "#page == 1", key = "1")
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
    @Cacheable(value = "topPosts", condition = "#amount == 10")
    public List<Post> getMostRatedPosts(int amount) {
        return this.<List<Post>>executeWithNativeSession(
                session -> session
                        .createQuery("from Post order by rating desc")
                        .setMaxResults(amount)
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
    @Caching(evict = {
            @CacheEvict(value = "firstPagePosts", allEntries = true),
            @CacheEvict(value = "allPosts", allEntries = true)
    })
    public long addPost(Post post) {
        post.setPostDate(new Date());
        return (Long) save(post);
    }

    @Override
    @Transactional(readOnly = false)
    @Caching(evict = {
            @CacheEvict(value = "firstPagePosts", allEntries = true),
            @CacheEvict(value = "allPosts", allEntries = true)
    })
    public void updatePost(Post post) {
        merge(post);
    }

    @Override
    @Transactional(readOnly = false)
    @Caching(evict = {
            @CacheEvict(value = "firstPagePosts", allEntries = true),
            @CacheEvict(value = "allPosts", allEntries = true)
    })
    public void deletePost(Post post) {
        delete(post);
    }

    @Override
    public int getPagesCount() {
        return getPagesCount(DEFAULT_POSTS_PER_PAGE);
    }

    @Override
    @Transactional(readOnly = true)
    public int getPagesCount(int postsPerPage) {
        if (postsPerPage <= 0) {
            throw new IllegalArgumentException("Number of posts per page should be greater than 0");
        }

        long postCount = (long) executeWithNativeSession(session ->
                session.createQuery("select count(*) from Post")
                        .list()
                        .get(0)
        );
        return (int) Math.ceil(postCount / (float) postsPerPage);
    }

}
