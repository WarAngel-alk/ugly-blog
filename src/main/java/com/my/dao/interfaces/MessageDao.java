package com.my.dao.interfaces;

import com.my.model.Message;
import com.my.model.User;

import java.util.List;

public interface MessageDao {

    public List<Message> getIncomingMessages(User user);
    
    public List<Message> getIncomingMessages(User user, int limit);

    public List<Message> getOutcomingMessages(User user);
    
    public List<Message> getOutcomingMessages(User user, int limit);
    
    public Message getMessage(long id);
    
    public void sendMessage(Message message);

}
