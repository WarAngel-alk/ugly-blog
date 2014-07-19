package com.my.dao;

import com.my.dao.interfaces.PostDao;
import com.my.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

@ContextConfiguration(locations = {"classpath:test-spring-config.xml"})
public class PostDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private PostDao postDao;

    private Post createPost() {
        Post post = new Post();
        post.setTitle("Post title");
        post.setText("Post text");

        return post;
    }

    private void checkPostParametersEqual(Post post1, Post post2) {
        assertEquals(post1.getTitle(), post2.getTitle());
        assertEquals(post1.getText(), post2.getText());
        assertEquals(post1.getPostDate(), post2.getPostDate());
    }

    @Test
    public void testReturnedId() throws Exception {
        Post post = createPost();

        long savedId = postDao.addPost(post);
        long receivedId = postDao.getPost(savedId).getId();

        assertEquals(receivedId, savedId);
    }

    @Test
    public void testAddAndGet() throws Exception {
        Post savedPost = createPost();

        postDao.addPost(savedPost);

        Post receivedPost = postDao.getPost(savedPost.getId());

        checkPostParametersEqual(savedPost, receivedPost);
    }

    @Test
    public void testGetAllPosts() throws Exception {
        for (int i = 0; i < 5; ++i) {
            Post post = createPost();
            postDao.addPost(post);
        }

        List<Post> postList = postDao.getPosts();

        assertEquals(postList.size(), 5);

        Post receivedPost = postList.get(0);

        checkPostParametersEqual(receivedPost, createPost());
    }

    @Test
    public void testUpdatePost() throws Exception {
        Post startPost = createPost();

        postDao.addPost(startPost);

        Post updatedPost = postDao.getPost(startPost.getId());
        updatedPost.setTitle("Updated title");
        updatedPost.setText("Updated text");

        postDao.updatePost(updatedPost);

        Post receivedUpdatedPost = postDao.getPost(startPost.getId());

        checkPostParametersEqual(receivedUpdatedPost, updatedPost);
    }

    @Test
    public void testGetPostsForPage() throws Exception {
        for (int i = 0; i < 15; ++i) {
            Post post = createPost();
            postDao.addPost(post);
        }

        List<Post> postList = postDao.getPostsForPage(2, 10);

        assertEquals(postList.size(), 5);

        Post receivedPost = postList.get(0);

        checkPostParametersEqual(receivedPost, createPost());

    }
}
