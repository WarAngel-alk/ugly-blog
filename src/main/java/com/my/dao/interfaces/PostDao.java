package com.my.dao.interfaces;

import com.my.model.Post;

import java.util.List;

public interface PostDao {

    public List<Post> getPosts();

    public List<Post> getPostsForPage(int page);

    public List<Post> getPostsForPage(int page, int postsPerPage);

    public Post getPost(long id);

    public long addPost(Post post);

    public void updatePost(Post post);

    void deletePost(Post post);
}
