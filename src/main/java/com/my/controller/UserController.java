package com.my.controller;

import com.my.dao.interfaces.UserDao;
import com.my.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String showSignupForm(Model model) {
        return "signup";
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String showUserPage(@PathVariable("id") long id, Model model) {
        return "user";
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public String addUser(@ModelAttribute("user") User user, Model model) {
        return "redirect:/home";
    }

}
