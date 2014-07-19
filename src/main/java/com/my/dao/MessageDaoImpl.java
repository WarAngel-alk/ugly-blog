package com.my.dao;

import com.my.dao.interfaces.MessageDao;
import com.my.model.Message;
import com.my.model.User;
import org.springframework.orm.hibernate4.HibernateTemplate;

import java.util.ArrayList;
import java.util.List;

public class MessageDaoImpl extends HibernateTemplate implements MessageDao {

    private static final int DEFAULT_MESSAGES_PER_PAGE = 20;

    private static final List<Message> EMPTY_MESSAGE_LIST = new ArrayList<>();

    @Override
    public List<Message> getIncomingMessages(User user) {
        return EMPTY_MESSAGE_LIST;
    }

    @Override
    public List<Message> getIncomingMessagesForPage(User user, int page) {
        return getIncomingMessagesForPage(user, page, DEFAULT_MESSAGES_PER_PAGE);
    }

    @Override
    public List<Message> getIncomingMessagesForPage(User user, int page, int messagePerPage) {
        return EMPTY_MESSAGE_LIST;
    }

    @Override
    public List<Message> getOutcomingMessages(User user) {
        return EMPTY_MESSAGE_LIST;
    }

    @Override
    public List<Message> getOutcomingMessagesForPage(User user, int page) {
        return getOutcomingMessagesForPage(user, page, DEFAULT_MESSAGES_PER_PAGE);
    }

    @Override
    public List<Message> getOutcomingMessagesForPage(User user, int page, int messagePerPage) {
        return EMPTY_MESSAGE_LIST;
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
