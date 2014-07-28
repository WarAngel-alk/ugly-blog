package com.my.controller;

import com.my.dao.interfaces.MarkDao;
import com.my.dao.interfaces.PostDao;
import com.my.dao.interfaces.UserDao;
import com.my.model.Post;
import com.my.model.User;
import com.my.model.UserPostMark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
public class MarkController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    private MarkDao markDao;

    @RequestMapping(value = "/post/{postId}/vote*", method = RequestMethod.PUT)
    public
    @ResponseBody
    String voteForPost(
            @PathVariable long postId, @RequestParam("mark") boolean vote, HttpServletResponse response) {

        String curUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User curUser = userDao.getUser(curUserName);

        Post post = postDao.getPost(postId);

        if (post.didUserVote(curUser)) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            return null;
        }

        UserPostMark mark = new UserPostMark(curUser, post, vote);

        markDao.addPostMark(mark);

        int positive = post.countPositiveMarks();
        int negative = post.countNegativeMarks();
        int rating = post.countRating();

        return positive + "," + negative + "," + rating;
    }

}
