package com.my.dao;

import com.my.dao.interfaces.MessageDao;
import com.my.dao.interfaces.UserDao;
import com.my.model.Message;
import com.my.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

@ContextConfiguration("classpath:test-spring-config.xml")
public class MessageDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageDao messageDao;

    private static User sender;
    private static User receiver;

    @BeforeClass
    public void setUp() throws Exception {
        sender = new User();
        sender.setName("sender");
        sender.setPass("sender");
        sender.setEmail("sender@email.em");
        sender.setAvatarPath("/1234.png");

        receiver = new User();
        receiver.setName("receiver");
        receiver.setPass("receiver");
        receiver.setEmail("receiver@email.em");
        receiver.setAvatarPath("/4321.png");

        userDao.addUser(sender);
        userDao.addUser(receiver);
    }

    @Test
    public void testIdReturned() throws Exception {
        Message sentMsg = new Message();
        sentMsg.setSender(sender);
        sentMsg.setReceiver(receiver);
        sentMsg.setText("Message text");

        long sentId = messageDao.sendMessage(sentMsg);
        long receivedId = messageDao.getMessage(sentId).getId();

        assertEquals(receivedId, sentId);
    }

    @Test
    public void testSendAndReceiveMessage() throws Exception {
        Message sentMsg = new Message();
        sentMsg.setSender(sender);
        sentMsg.setReceiver(receiver);
        sentMsg.setText("Message text");

        long id = messageDao.sendMessage(sentMsg);

        Message receivedMsg = messageDao.getMessage(id);

        assertEquals(receivedMsg.getSender().getId(), sender.getId());
        assertEquals(receivedMsg.getReceiver().getId(), receiver.getId());
        assertEquals(receivedMsg.getText(), "Message text");
    }

    @Test
    public void testFlagsSet() throws Exception {
        Message sentMsg = new Message();
        sentMsg.setSender(sender);
        sentMsg.setReceiver(receiver);
        sentMsg.setText("Message text");

        messageDao.sendMessage(sentMsg);

        Assert.assertFalse(sentMsg.isDeletedBySender());
        Assert.assertFalse(sentMsg.isDeletedByReceiver());
        Assert.assertFalse(sentMsg.isRead());
    }

    @Test
    public void testDateNotNull() throws Exception {
        Message sentMsg = new Message();
        sentMsg.setSender(sender);
        sentMsg.setReceiver(receiver);
        sentMsg.setText("Message text");

        messageDao.sendMessage(sentMsg);

        Assert.assertNotNull(sentMsg.getDate());
    }

    @Test
    public void testGetIncomingMessages() throws Exception {
        Message sentMsg = new Message();
        sentMsg.setSender(sender);
        sentMsg.setReceiver(receiver);
        sentMsg.setText("Message text");

        messageDao.sendMessage(sentMsg);

        List<Message> messageList = messageDao.getIncomingMessages(receiver);

        assertEquals(messageList.size(), 1);

        Message msg = messageList.get(0);

        assertEquals(msg.getSender().getId(), sender.getId());
        assertEquals(msg.getReceiver().getId(), receiver.getId());
        assertEquals(msg.getText(), "Message text");
    }

    @Test
    public void testGetOutcomingMessages() throws Exception {
        Message sentMsg = new Message();
        sentMsg.setSender(sender);
        sentMsg.setReceiver(receiver);
        sentMsg.setText("Message text");

        messageDao.sendMessage(sentMsg);

        List<Message> messageList = messageDao.getOutcomingMessages(sender);

        assertEquals(messageList.size(), 1);

        Message msg = messageList.get(0);

        assertEquals(msg.getSender().getId(), sender.getId());
        assertEquals(msg.getReceiver().getId(), receiver.getId());
        assertEquals(msg.getText(), "Message text");
    }

    @Test
    public void testGetIncomingMessagesByPage() throws Exception {
        for (int i = 0; i < 30; ++i) {
            Message sentMsg = new Message();
            sentMsg.setSender(sender);
            sentMsg.setReceiver(receiver);
            sentMsg.setText("Message text");

            messageDao.sendMessage(sentMsg);
        }

        List<Message> messageList = messageDao.getIncomingMessagesForPage(receiver, 2, 20);

        assertEquals(messageList.size(), 10);

        Message msg = messageList.get(0);

        assertEquals(msg.getSender().getId(), sender.getId());
        assertEquals(msg.getReceiver().getId(), receiver.getId());
        assertEquals(msg.getText(), "Message text");
    }

    @Test
    public void testGetOutcomingMessagesByPage() throws Exception {
        for (int i = 0; i < 30; ++i) {
            Message sentMsg = new Message();
            sentMsg.setSender(sender);
            sentMsg.setReceiver(receiver);
            sentMsg.setText("Message text");

            messageDao.sendMessage(sentMsg);
        }

        List<Message> messageList = messageDao.getOutcomingMessagesForPage(sender, 2, 20);

        assertEquals(messageList.size(), 10);

        Message msg = messageList.get(0);

        assertEquals(msg.getSender().getId(), sender.getId());
        assertEquals(msg.getReceiver().getId(), receiver.getId());
        assertEquals(msg.getText(), "Message text");
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setMessageDao(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

}
