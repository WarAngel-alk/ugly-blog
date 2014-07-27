package com.my.controller;

import com.my.dao.interfaces.MessageDao;
import com.my.dao.interfaces.UserDao;
import com.my.model.Message;
import com.my.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class MessageController {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/mail/in", method = RequestMethod.GET)
    public String showIncomingMessages(Model model) {
        String curUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User curUser = userDao.getUser(curUserName);

        List<Message> messageList = messageDao.getIncomingMessagesForPage(curUser, 1);
        model.addAttribute("messageList", messageList);

        model.addAttribute("isInbox", true);

        return "mailbox";
    }

    @RequestMapping(value = "/mail/out", method = RequestMethod.GET)
    public String showOutcomingMessages(Model model) {
        String curUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User curUser = userDao.getUser(curUserName);

        List<Message> messageList = messageDao.getOutcomingMessagesForPage(curUser, 1);
        model.addAttribute("messageList", messageList);

        model.addAttribute("isInbox", false);

        return "mailbox";
    }

    @RequestMapping(value = "/mail/message/{id}", method = RequestMethod.GET)
    public String showMessage(@PathVariable("id") long id, Model model) {
        Message message = messageDao.getMessage(id);
        model.addAttribute("message", message);

        return "message";
    }

    @RequestMapping(value = "/mail/send", method = RequestMethod.GET)
    public String showSendMessageForm(Model model) {
        model.addAttribute("message", new Message());

        return "sendMessage";
    }

    @RequestMapping(value = "/mail/send", method = RequestMethod.PUT)
    public String sendMessage(@ModelAttribute("message") Message message, Model model) {
        String curUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User curUser = userDao.getUser(curUserName);
        message.setSender(curUser);

        messageDao.sendMessage(message);

        return "redirect:/mail/out";
    }

    @RequestMapping(value = "/mail/message/{id}", method = RequestMethod.DELETE)
    public String deleteMessage(@PathVariable("id") long id, Model model) {
        messageDao.deleteMessage(id);

        return "redirect:/mail/in";
    }

}
