package com.my.controller;

import com.my.dao.interfaces.PostDao;
import com.my.dao.interfaces.TagDao;
import com.my.dao.interfaces.UserDao;
import com.my.model.Comment;
import com.my.model.Post;
import com.my.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    private PostDao postDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TagDao tagDao;

    @RequestMapping(value = {"/", "/home", "/posts*"}, method = RequestMethod.GET)
    public String showHomePage(Model model) {
        return showPostListByPage(1, model);
    }

    @RequestMapping(value = "/posts/page/{page}", method = RequestMethod.GET)
    public String showPostListByPage(@PathVariable int page, Model model) {
        List<Post> postList = postDao.getPostsForPage(page);
        int maxPages = postDao.getPagesCount();

        if (postList.isEmpty()) {
            model.addAttribute("tipMessage", "There isn't posts :(");
        } else {
            model.addAttribute("postList", postList);
        }
        model.addAttribute("currentPage", page);
        model.addAttribute("maxPages", maxPages);

        return "posts";
    }

    @RequestMapping(value = "/post/{id}", method = RequestMethod.GET)
    public String showPost(@PathVariable("id") long id, Model model) {
        model.addAttribute("post", postDao.getPost(id));
        model.addAttribute("newComment", new Comment());

        return "post";
    }

    @RequestMapping(value = "/post/add", method = RequestMethod.GET)
    public String showAddPostForm(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("isNewPost", true);
        return "editPost";
    }

    @RequestMapping(value = "/post/{id}/edit", method = RequestMethod.GET)
    public String showEditPostForm(@PathVariable("id") long id, Model model) {
        Post post = postDao.getPost(id);

        model.addAttribute("post", post);
        model.addAttribute("isNewPost", false);

        model.addAttribute("tagsString", post.getTagsString());

        return "editPost";
    }

    @RequestMapping(value = "/post", method = RequestMethod.PUT)
    public String addPost(@ModelAttribute("post") @Valid Post post,
                          BindingResult bindResult,
                          @RequestParam("tagsString") String tagsString,
                          Model model) {
        if (bindResult.hasErrors()) {
            model.addAttribute("isNewPost", true);
            model.addAttribute("tagsString", tagsString);
            return "editPost";
        }

        postDao.addPost(post);

        processTags(post, tagsString);

        postDao.updatePost(post);

        return "redirect:/post/" + post.getId();
    }

    @RequestMapping(value = "/post/{id}*", method = RequestMethod.POST)
    public String updatePost(
            @ModelAttribute("post") @Valid Post post,
            BindingResult bindResult,
            @RequestParam(value = "tagsString", defaultValue = "") String tagsString,
            @PathVariable("id") long id,
            Model model) {
        if (bindResult.hasErrors()) {
            model.addAttribute("isNewPost", false);
            model.addAttribute("tagsString", tagsString);
            return "editPost";
        }

        Post postForUpdate = postDao.getPost(post.getId());

        postForUpdate.setTitle(post.getTitle());
        postForUpdate.setText(post.getText());

        processTags(postForUpdate, tagsString);

        postDao.updatePost(postForUpdate);

        return "redirect:/post/" + postForUpdate.getId();
    }

    /**
     * Да, я не умею Hibernate. Когда-нибудь я вернусь к этому методу с чувством позора.
     */
    private void processTags(Post post, String tagsString) {
        List<Tag> originalTagList = post.getTags();
        List<String> postTagNames = null;

        if (!tagsString.isEmpty()) {
            postTagNames = Arrays.asList(tagsString.split(","));

            post.setTags(new ArrayList<Tag>(postTagNames.size()));

            List<String> allTagNames = tagDao.getAllTagNames();
            for (int i = 0; i < postTagNames.size(); i++) {
                String tagName = postTagNames.get(i);
                tagName = tagName.trim();
                postTagNames.set(i, tagName);
                Tag tag = null;
                if (allTagNames.contains(tagName)) {
                    tag = tagDao.getTag(tagName);
                    if (!tag.getPosts().contains(post)) {
                        tag.getPosts().add(post);
                    }
                } else {
                    tag = new Tag(tagName);
                    tag.setPosts(Arrays.asList(post));
                }

                post.getTags().add(tag);
            }

            if (originalTagList != null) {
                for (Tag originalTag : originalTagList) {
                    if (!postTagNames.contains(originalTag.getName())) {
                        originalTag.getPosts().remove(post);
                        if (originalTag.getPosts().size() == 0) {
                            tagDao.deleteTag(originalTag);
                        }
                    }
                }
            }
        }
    }

    @RequestMapping(value = "/post/{id}", method = RequestMethod.DELETE)
    public String deletePost(@PathVariable("id") long id, Model model) {
        Post post = postDao.getPost(id);
        postDao.deletePost(post);

        return "redirect:/home";
    }

    @RequestMapping(value = "/posts/tag/{tagName}*", method = RequestMethod.GET)
    public String showPostsByTag(@PathVariable String tagName, ModelMap model) {
        Tag tag = tagDao.getTag(tagName);
        List<Post> postsList = postDao.getPostsByTagForPage(tag, 1);

        model.put("tipMessage", "All posts with tag <b>" + tagName + "</b>");
        model.put("postList", postsList);

        return "posts";
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public PostDao getPostDao() {
        return postDao;
    }
}
