package com.my.dao;

import com.my.dao.interfaces.PostDao;
import com.my.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.List;

import static com.my.test.DomainObjectComparator.assertDeepEquals;
import static org.testng.Assert.assertEquals;

@ContextConfiguration(locations = {"classpath:test-spring-config.xml"})
public class PostDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private PostDao postDao;

    @Test
    public void testReturnedId() throws Exception {
        Post post = new Post("Post title", "Post text");

        long savedId = postDao.addPost(post);
        long receivedId = postDao.getPost(savedId).getId();

        assertEquals(receivedId, savedId);
    }

    @Test
    public void testAddAndGet() throws Exception {
        Post savedPost = new Post("Post title", "Post text");

        postDao.addPost(savedPost);

        Post receivedPost = postDao.getPost(savedPost.getId());

        assertDeepEquals(savedPost, receivedPost);
    }

    @Test
    public void testGetAllPosts() throws Exception {
        Post sentFirstPost = new Post("Post title", "Post text");
        postDao.addPost(sentFirstPost);
        for (int i = 1; i < 5; ++i) {
            Post post = new Post("Post title", "Post text");
            postDao.addPost(post);
        }

        List<Post> postList = postDao.getPosts();

        assertEquals(postList.size(), 5);

        Post receivedFirstPost = postList.get(0);

        assertDeepEquals(receivedFirstPost, sentFirstPost);
    }

    @Test
    public void testUpdatePost() throws Exception {
        Post startPost = new Post("Post title", "Post text");

        postDao.addPost(startPost);

        Post updatedPost = postDao.getPost(startPost.getId());
        updatedPost.setTitle("Updated title");
        updatedPost.setText("Updated text");

        postDao.updatePost(updatedPost);

        Post receivedUpdatedPost = postDao.getPost(updatedPost.getId());

        assertDeepEquals(receivedUpdatedPost, updatedPost);
    }

    @Test
    public void testGetPostsForPage() throws Exception {
        Post sentFirstPost = new Post("Post title", "Post text");
        postDao.addPost(sentFirstPost);
        for (int i = 1; i < 15; ++i) {
            Post post = new Post("Post title", "Post text");
            postDao.addPost(post);
        }

        List<Post> postList = postDao.getPostsForPage(2, 10);

        assertEquals(postList.size(), 5);

        Post receivedFirstPost = postList.get(0);

        assertDeepEquals(receivedFirstPost, sentFirstPost);
    }
}
