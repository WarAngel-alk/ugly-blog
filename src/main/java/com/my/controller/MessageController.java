package com.my.controller;

import com.my.dao.interfaces.MessageDao;
import com.my.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MessageController {

    @Autowired
    private MessageDao messageDao;

    @RequestMapping(value = "/mail/in", method = RequestMethod.GET)
    public String showIncomingMessages(Model model) {
        return "mailbox";
    }

    @RequestMapping(value = "/mail/out", method = RequestMethod.GET)
    public String showOutcomingMessages(Model model) {
        return "mailbox";
    }

    @RequestMapping(value = "/mail/message/{id}", method = RequestMethod.GET)
    public String showMessage(@PathVariable("id") long id, Model model) {
        return "message";
    }

    @RequestMapping(value = "/mail/send", method = RequestMethod.GET)
    public String showSendMessageForm(Model model) {
        return "sendMessage";
    }

    @RequestMapping(value = "/mail/send", method = RequestMethod.POST)
    public String sendMessage(@ModelAttribute("message") Message message, Model model) {
        return "redirect:/mail/out";
    }

    @RequestMapping(value = "/mail/message/{id}", method = RequestMethod.DELETE)
    public String deleteMessage(Model model) {
        return "redirect:/mail/in";
    }

}
