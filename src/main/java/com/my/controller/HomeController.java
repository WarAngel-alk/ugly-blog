package com.my.controller;

import com.my.dao.interfaces.PostDao;
import com.my.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private PostDao postDao;

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String showHomePage(Model model) {
        List<Post> postList = postDao.getPostsForPage(1);

        model.addAttribute("postList", postList);

        return "home";
    }

}
