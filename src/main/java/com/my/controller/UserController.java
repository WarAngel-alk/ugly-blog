package com.my.controller;

import com.my.dao.interfaces.UserDao;
import com.my.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/signup*", method = RequestMethod.GET)
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @RequestMapping(value = "/login*", method = RequestMethod.GET)
    public String showLoginForm(
            @RequestParam(value = "error", required = false) boolean error,
            Model model) {
        if (error) {
            model.addAttribute("errorMessage", "Something is wrong");
        }
        return "login";
    }

    @RequestMapping(value = "/user/{id}*", method = RequestMethod.GET)
    public String showUserPage(@PathVariable("id") long id, Model model) {
        User user = userDao.getUser(id);

        model.addAttribute("user", user);

        return "user";
    }

    @RequestMapping(value = "/user*", method = RequestMethod.PUT)
    public String addUser(
            @ModelAttribute("user") User user,
            Model model) {
        userDao.addUser(user);

        return "redirect:user/" + user.getId();
    }

}
