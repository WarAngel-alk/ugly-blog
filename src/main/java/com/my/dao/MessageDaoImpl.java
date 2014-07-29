package com.my.dao;

import com.my.dao.interfaces.MessageDao;
import com.my.model.Message;
import com.my.model.User;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public class MessageDaoImpl extends HibernateTemplate implements MessageDao {

    private static final int DEFAULT_MESSAGES_PER_PAGE = 20;

    @Override
    @Transactional(readOnly = true)
    public List<Message> getIncomingMessages(User user) {
        return (List<Message>) find("from Message where receiver = ?", user);
    }

    @Override
    public List<Message> getIncomingMessagesForPage(User user, int page) {
        return getIncomingMessagesForPage(user, page, DEFAULT_MESSAGES_PER_PAGE);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Message> getIncomingMessagesForPage(User user, int page, int messagePerPage) {
        return this.<List<Message>>executeWithNativeSession(
                session -> session
                        .createQuery("from Message where receiver = :receiver and deletedByReceiver = false")
                        .setParameter("receiver", user)
                        .setFirstResult((page - 1) * messagePerPage)
                        .setMaxResults(messagePerPage)
                        .list());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Message> getOutcomingMessages(User user) {
        return (List<Message>) find("from Message where sender = ?", user);
    }

    @Override
    public List<Message> getOutcomingMessagesForPage(User user, int page) {
        return getOutcomingMessagesForPage(user, page, DEFAULT_MESSAGES_PER_PAGE);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Message> getOutcomingMessagesForPage(User user, int page, int messagePerPage) {
        return this.<List<Message>>executeWithNativeSession(
                session -> session
                        .createQuery("from Message where sender = :sender and deletedBySender = false")
                        .setParameter("sender", user)
                        .setFirstResult((page - 1) * messagePerPage)
                        .setMaxResults(messagePerPage)
                        .list());
    }

    @Override
    @Transactional(readOnly = true)
    public Message getMessage(long id) {
        return get(Message.class, id);
    }

    @Override
    @Transactional(readOnly = false)
    public long sendMessage(Message message) {
        message.setDeletedBySender(false);
        message.setDeletedByReceiver(false);
        message.setRead(false);
        message.setDate(new Date());

        return (Long) save(message);
    }

    @Override
    public void deleteMessage(long id) {
        executeWithNativeSession(session ->
                session.createQuery("delete from Message where id = :id")
                        .setParameter("id", id)
                        .executeUpdate()
        );
    }

}
