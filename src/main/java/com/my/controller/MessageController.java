package com.my.controller;

import com.my.dao.interfaces.MessageDao;
import com.my.dao.interfaces.UserDao;
import com.my.model.Message;
import com.my.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        model.addAttribute("isOutbox", false);
        model.addAttribute("isNewMessage", false);

        return "mailbox";
    }

    @RequestMapping(value = "/mail/out", method = RequestMethod.GET)
    public String showOutcomingMessages(Model model) {
        String curUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User curUser = userDao.getUser(curUserName);

        List<Message> messageList = messageDao.getOutcomingMessagesForPage(curUser, 1);
        model.addAttribute("messageList", messageList);

        model.addAttribute("isInbox", false);
        model.addAttribute("isOutbox", true);
        model.addAttribute("isNewMessage", false);

        return "mailbox";
    }

    @RequestMapping(value = "/mail/message/{id}", method = RequestMethod.GET)
    public String showMessage(@PathVariable("id") long id, Model model) {
        Message message = messageDao.getMessage(id);
        model.addAttribute("message", message);

        String curUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User curUser = userDao.getUser(curUserName);
        if (curUser.equals(message.getReceiver())) {
            message.setRead(true);
            messageDao.updateMessage(message);
        }

        Message newMessage = new Message();
        newMessage.setReceiver(message.getSender());
        model.addAttribute("newMessage", newMessage);

        return "message";
    }

    @RequestMapping(value = "/mail/send", method = RequestMethod.GET)
    public String showSendMessageForm(@RequestParam(value = "receiver", required = false) String receiverName, Model model) {
        Message message = new Message();
        if (receiverName != null) {
            message.setReceiver(new User(receiverName));
        }

        model.addAttribute("newMessage", message);

        model.addAttribute("isInbox", false);
        model.addAttribute("isOutbox", false);
        model.addAttribute("isNewMessage", true);

        return "sendMessage";
    }

    @RequestMapping(value = "/mail/send", method = RequestMethod.PUT)
    public String sendMessage(@ModelAttribute("newMessage") @Valid Message message,
                              BindingResult bindResult,
                              Model model) {
        String receiverName = message.getReceiver().getName();
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();

        boolean anyErrors = false;
        if (bindResult.hasErrors()) {
            anyErrors = true;
        }
        if (userDao.isUsernameFree(receiverName)) {
            bindResult.addError(new FieldError("message", "receiver", "Receiver not exist"));
            anyErrors = true;
        }
        if (currentUserName.equals(receiverName)) {
            bindResult.addError(new FieldError("message", "receiver", "You can not send message to yourself"));
            anyErrors = true;
        }

        if (anyErrors) {
            model.addAttribute("isInbox", false);
            model.addAttribute("isOutbox", false);
            model.addAttribute("isNewMessage", true);
            return "sendMessage";
        }

        User curUser = userDao.getUser(currentUserName);
        message.setSender(curUser);

        User receiver = userDao.getUser(receiverName);
        message.setReceiver(receiver);

        messageDao.sendMessage(message);

        return "redirect:/mail/out";
    }

    @ResponseBody
    @RequestMapping(value = "/mail/message/{id}", method = RequestMethod.DELETE)
    public String deleteMessage(@PathVariable("id") long id, Model model,
                                @RequestParam boolean byReceiver) {
        Message message = messageDao.getMessage(id);

        if (byReceiver) {
            message.setDeletedByReceiver(true);
            message.setRead(true);
        } else {
            message.setDeletedBySender(true);
        }

        if (message.isDeletedByReceiver() && message.isDeletedBySender()) {
            messageDao.deleteMessage(message.getId());
        } else {
            messageDao.updateMessage(message);
        }

        return "";
    }

}
