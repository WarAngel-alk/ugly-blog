package com.my.dao.interfaces;

import com.my.model.Message;
import com.my.model.User;

import java.util.List;

public interface MessageDao {

    public List<Message> getIncomingMessages(User user);

    public List<Message> getIncomingMessagesForPage(User user, int page);

    public List<Message> getOutcomingMessages(User user);

    public List<Message> getOutcomingMessagesForPage(User user, int page);

    public Message getMessage(long id);

    public long sendMessage(Message message);

}
