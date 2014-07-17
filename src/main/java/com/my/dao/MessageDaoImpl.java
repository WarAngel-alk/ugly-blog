package com.my.dao;

import com.my.dao.interfaces.MessageDao;
import com.my.model.Message;
import com.my.model.User;

import java.util.Arrays;
import java.util.List;

public class MessageDaoImpl implements MessageDao {

    @Override
    public List<Message> getIncomingMessages(User user) {
        return Arrays.asList();
    }

    @Override
    public List<Message> getIncomingMessagesForPage(User user, int page) {
        return Arrays.asList();
    }

    @Override
    public List<Message> getOutcomingMessages(User user) {
        return Arrays.asList();
    }

    @Override
    public List<Message> getOutcomingMessagesForPage(User user, int page) {
        return Arrays.asList();
    }

    @Override
    public Message getMessage(long id) {
        return new Message();
    }

    @Override
    public void sendMessage(Message message) {

    }

}
