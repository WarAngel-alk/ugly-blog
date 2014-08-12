package com.my.controller;

import com.my.dao.interfaces.CommentDao;
import com.my.model.Comment;
import com.my.model.Post;
import com.my.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class CommentController {

    @Autowired
    public CommentDao commentDao;

    @Autowired
    public PostController postController;

    @RequestMapping(value = "/post/{postId}/comment*", method = RequestMethod.PUT)
    public String addComment(@PathVariable("postId") long postId,
                             @ModelAttribute("newComment") @Valid Comment comment,
                             BindingResult bindResult,
                             Model model) {
        if (bindResult.hasErrors()) {
            model.addAttribute("post", postController.getPostDao().getPost(postId));
            return "post";
        }

        User author = postController.getUserDao().getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        comment.setAuthor(author);

        Post post = postController.getPostDao().getPost(postId);
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