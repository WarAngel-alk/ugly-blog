package com.my.controller;

import com.my.dao.interfaces.PostDao;
import com.my.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PostController {

    @Autowired
    private PostDao postDao;

    @RequestMapping(value = "/post/{id}", method = RequestMethod.GET)
    public String showPost(@PathVariable("id") long id, Model model) {
        return "post";
    }

    @RequestMapping(value = "/post/add", method = RequestMethod.GET)
    public String showAddPostForm(Model model) {
        return "editPost";
    }

    @RequestMapping(value = "/post/{id}/edit", method = RequestMethod.GET)
    public String showEditPostForm(@PathVariable("id") long id, Model model) {
        return "editPost";
    }

    @RequestMapping(value = "/post", method = RequestMethod.PUT)
    public String addPost(@ModelAttribute("post") Post post, Model model) {
        return "redirect:/post/" + 42L;
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public String updatePost(@ModelAttribute("post") Post post, Model model) {
        return "redirect:/post/" + 42L;
    }

    @RequestMapping(value = "/post/{id}", method = RequestMethod.DELETE)
    public String deletePost(@PathVariable("id") long id, Model model) {
        return "redirect:/home";
    }

}
