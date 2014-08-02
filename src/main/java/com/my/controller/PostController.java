package com.my.controller;

import com.my.dao.interfaces.CommentDao;
import com.my.dao.interfaces.PostDao;
import com.my.dao.interfaces.TagDao;
import com.my.dao.interfaces.UserDao;
import com.my.model.Comment;
import com.my.model.Post;
import com.my.model.Tag;
import com.my.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private CommentDao commentDao;

    @Autowired
    private TagDao tagDao;

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

    @RequestMapping(value = "/post/{id}", method = RequestMethod.DELETE)
    public String deletePost(@PathVariable("id") long id, Model model) {
        Post post = postDao.getPost(id);
        postDao.deletePost(post);

        return "redirect:/home";
    }

    @RequestMapping(value = "/post/{postId}/comment*", method = RequestMethod.PUT)
    public String addComment(@PathVariable("postId") long postId,
                             @ModelAttribute("newComment") @Valid Comment comment,
                             BindingResult bindResult,
                             Model model) {
        if (bindResult.hasErrors()) {
            model.addAttribute("post", postDao.getPost(postId));
            return "post";
        }

        User author = userDao.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        comment.setAuthor(author);

        Post post = postDao.getPost(postId);
        comment.setPost(post);

        commentDao.addComment(comment);

        return "redirect:/post/" + postId;
    }

    @ResponseBody
    @RequestMapping(value = "/post/{postId}/comment/{commentId}*", method = RequestMethod.DELETE)
    public String deleteComment(@PathVariable long postId, @PathVariable long commentId) {
        Comment comment = commentDao.getComment(commentId);
        commentDao.deleteComment(comment);

        return "";
    }

}
