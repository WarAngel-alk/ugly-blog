package com.my.dao.interfaces;

import com.my.model.Post;

import java.util.List;

public interface PostDao {

    public List<Post> getRecentPosts();

    public List<Post> getPostsForPage(int page);

    public Post getPost(long id);

    public long addPost(Post post);

    public void updatePost(Post post);

}
