package com.my.dao;

import com.my.dao.interfaces.MessageDao;
import com.my.model.Message;
import com.my.model.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public class MessageDaoImpl extends HibernateTemplate implements MessageDao {

    private static final int DEFAULT_MESSAGES_PER_PAGE = 10;

    @Override
    @Transactional(readOnly = true)
    public List<Message> getIncomingMessages(User user) {
        return (List<Message>) find("from Message where receiver = ? and deletedByReceiver = false", user);
    }

    @Override
    @Cacheable(value = "incomingMessages", key = "#user.id", condition = "#page == 1")
    public List<Message> getIncomingMessagesForPage(User user, int page) {
        return getIncomingMessagesForPage(user, page, DEFAULT_MESSAGES_PER_PAGE);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Message> getIncomingMessagesForPage(User user, int page, int messagePerPage) {
        return this.<List<Message>>executeWithNativeSession(
                session -> session
                        .createQuery("from Message where receiver = :receiver and deletedByReceiver = false order by date desc")
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
    @Cacheable(value = "outcomingMessages", key = "#user.id", condition = "#page == 1")
    public List<Message> getOutcomingMessagesForPage(User user, int page) {
        return getOutcomingMessagesForPage(user, page, DEFAULT_MESSAGES_PER_PAGE);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Message> getOutcomingMessagesForPage(User user, int page, int messagePerPage) {
        return this.<List<Message>>executeWithNativeSession(
                session -> session
                        .createQuery("from Message where sender = :sender and deletedBySender = false order by date desc")
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
    @Caching(evict = {
            @CacheEvict(value = "incomingMessages", allEntries = false, key = "#message.receiver.id"),
            @CacheEvict(value = "outcomingMessages", allEntries = false, key = "#message.sender.id")
    })
    public long sendMessage(Message message) {
        message.setDeletedBySender(false);
        message.setDeletedByReceiver(false);
        message.setRead(false);
        message.setDate(new Date());

        return (Long) save(message);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteMessage(long id) {
        executeWithNativeSession(session ->
                session.createQuery("delete from Message where id = :id")
                        .setParameter("id", id)
                        .executeUpdate()
        );
    }

    @Override
    @Transactional(readOnly = false)
    @Caching(evict = {
            @CacheEvict(value = "incomingMessages", allEntries = false, key = "#message.receiver.id"),
            @CacheEvict(value = "outcomingMessages", allEntries = false, key = "#message.sender.id")
    })
    public void updateMessage(Message message) {
        update(message);
    }

    @Override
    public int getIncomingMessagesPagesCount(User receiver) {
        return getIncomingMessagesPagesCount(receiver, DEFAULT_MESSAGES_PER_PAGE);
    }

    @Override
    @Transactional(readOnly = true)
    public int getIncomingMessagesPagesCount(User receiver, int messagesPerPage) {
        if (messagesPerPage <= 0) {
            throw new IllegalArgumentException("Number of messages per page should be greater than 0");
        }

        long messageCount = (long) executeWithNativeSession(session ->
                session.createQuery("select count(*) from Message where receiver = :receiver and deletedByReceiver = false")
                        .setParameter("receiver", receiver)
                        .list()
                        .get(0)
        );
        return (int) Math.ceil(messageCount / (float) messagesPerPage);
    }

    @Override
    public int getOutcomingMessagesPagesCount(User sender) {
        return getOutcomingMessagesPagesCount(sender, DEFAULT_MESSAGES_PER_PAGE);
    }

    @Override
    @Transactional(readOnly = true)
    public int getOutcomingMessagesPagesCount(User sender, int messagesPerPage) {
        if (messagesPerPage <= 0) {
            throw new IllegalArgumentException("Number of messages per page should be greater than 0");
        }

        long messageCount = (long) executeWithNativeSession(session ->
                session.createQuery("select count(*) from Message where sender = :sender and deletedBySender = false")
                        .setParameter("sender", sender)
                        .list()
                        .get(0)
        );
        return (int) Math.ceil(messageCount / (float) messagesPerPage);
    }

}
