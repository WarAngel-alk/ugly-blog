package com.my.dao;

import com.my.dao.interfaces.MessageDao;
import com.my.model.Message;
import com.my.model.User;
import org.springframework.orm.hibernate4.HibernateTemplate;

import java.util.Arrays;
import java.util.List;

public class MessageDaoImpl extends HibernateTemplate implements MessageDao {

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
    public long sendMessage(Message message) {
        return -1L;
    }

}
