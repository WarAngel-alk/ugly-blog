package com.my.dao;

import com.my.dao.interfaces.MessageDao;
import com.my.dao.interfaces.UserDao;
import com.my.model.Message;
import com.my.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.my.test.DomainObjectComparator.assertDeepEquals;
import static org.testng.Assert.*;

@ContextConfiguration("classpath:test-spring-config.xml")
public class MessageDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageDao messageDao;

    private User sender;
    private User receiver;

    @BeforeMethod
    public void userCreating() throws Exception {
        sender = new User("sender", "sender", "sender@send.er");
        receiver = new User("receiver", "receiver", "receiver@email.em");

        userDao.addUser(sender);
        userDao.addUser(receiver);
    }

    @Test
    public void testIdReturned() throws Exception {
        Message sentMsg = new Message(sender, receiver, "Message text");

        long sentId = messageDao.sendMessage(sentMsg);
        Message receivedMsg = messageDao.getMessage(sentId);

        assertDeepEquals(receivedMsg, sentMsg);
    }

    @Test
    public void testSendAndReceiveMessage() throws Exception {
        Message sentMsg = new Message(sender, receiver, "Message text");

        long id = messageDao.sendMessage(sentMsg);

        Message receivedMsg = messageDao.getMessage(id);

        assertDeepEquals(receivedMsg, sentMsg);
    }

    @Test
    public void testFlagsSet() throws Exception {
        Message sentMsg = new Message(sender, receiver, "Message text");

        messageDao.sendMessage(sentMsg);

        assertFalse(sentMsg.isDeletedBySender());
        assertFalse(sentMsg.isDeletedByReceiver());
        assertFalse(sentMsg.isRead());
    }

    @Test
    public void testDateNotNull() throws Exception {
        Message sentMsg = new Message(sender, receiver, "Message text");

        messageDao.sendMessage(sentMsg);

        assertNotNull(sentMsg.getDate());
    }

    @Test
    public void testGetIncomingMessages() throws Exception {
        Message sentMsg = new Message(sender, receiver, "Message text");

        messageDao.sendMessage(sentMsg);

        List<Message> messageList = messageDao.getIncomingMessages(receiver);

        assertEquals(messageList.size(), 1);

        Message msg = messageList.get(0);

        assertDeepEquals(msg, sentMsg);
    }

    @Test
    public void testGetOutcomingMessages() throws Exception {
        Message sentMsg = new Message(sender, receiver, "Message text");

        messageDao.sendMessage(sentMsg);

        List<Message> messageList = messageDao.getOutcomingMessages(sender);

        assertEquals(messageList.size(), 1);

        Message receivedMsg = messageList.get(0);

        assertDeepEquals(receivedMsg, sentMsg);
    }

    @Test
    public void testGetIncomingMessagesByPage() throws Exception {
        Message sentFirstMsg = new Message(sender, receiver, "Message text");
        messageDao.sendMessage(sentFirstMsg);

        for (int i = 1; i < 30; ++i) {
            Message sentMsg = new Message(sender, receiver, "Message text");
            messageDao.sendMessage(sentMsg);
        }

        List<Message> messageList = messageDao.getIncomingMessagesForPage(receiver, 2, 20);

        assertEquals(messageList.size(), 10);

        Message receivedFirstMsg = messageList.get(0);

        assertDeepEquals(receivedFirstMsg, sentFirstMsg);
    }

    @Test
    public void testGetOutcomingMessagesByPage() throws Exception {
        Message sentFirstMsg = new Message(sender, receiver, "Message text");
        messageDao.sendMessage(sentFirstMsg);

        for (int i = 1; i < 30; ++i) {
            Message sentMsg = new Message(sender, receiver, "Message text");
            messageDao.sendMessage(sentMsg);
        }

        List<Message> messageList = messageDao.getOutcomingMessagesForPage(sender, 2, 20);

        assertEquals(messageList.size(), 10);

        Message receivedFirstMsg = messageList.get(0);

        assertDeepEquals(receivedFirstMsg, sentFirstMsg);
    }

}
