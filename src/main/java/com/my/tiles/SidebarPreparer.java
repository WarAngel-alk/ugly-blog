package com.my.tiles;

import com.my.dao.interfaces.PostDao;
import com.my.dao.interfaces.TagDao;
import com.my.model.Post;
import com.my.model.Tag;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.preparer.ViewPreparer;
import org.apache.tiles.request.Request;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SidebarPreparer implements ViewPreparer {

    @Autowired
    private TagDao tagDao;
    @Autowired
    private PostDao postDao;

    @Override
    public void execute(Request tilesContext, AttributeContext attributeContext) {
        List<Tag> tagList = tagDao.getAllTags();
        // TODO: Replace with 10 most rated posts
        List<Post> postList = postDao.getPostsForPage(1, 10);

        tilesContext.getContext("request").put("tagList", tagList);
        tilesContext.getContext("request").put("postList", postList);
    }
}
