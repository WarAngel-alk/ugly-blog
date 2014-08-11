package com.my.tiles;

import com.my.dao.interfaces.PostDao;
import com.my.dao.interfaces.TagDao;
import com.my.model.Post;
import com.my.model.Tag;
import com.my.util.PostUtil;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.preparer.ViewPreparer;
import org.apache.tiles.request.Request;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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

        Map<Integer, Map<Integer, List<Post>>> postMap = PostUtil.groupPostsByYearAndMonth(postDao.getPosts());

        tilesContext.getContext("request").put("tagList", tagList);
        tilesContext.getContext("request").put("postList", postList);
        tilesContext.getContext("request").put("postMap", postMap);
    }

    public static int getMaxFrequency(Collection<Tag> tags) {
        int max = 0;
        for (Tag tag : tags) {
            max = Math.max(tag.getPosts().size(), max);
        }

        return max;
    }

    public static double getRelativeTagFrequency(Tag tag, int maxFrequency) {
        double frequency = ((double) tag.getPosts().size()) / maxFrequency;

        if (frequency < 0.4) {
            frequency = 0.4;
        }

        frequency = ((int) (frequency * 10)) / 10d;
        return frequency;
    }

}
