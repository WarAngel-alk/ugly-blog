package com.my.test;

import com.my.model.*;

import java.util.Date;

import static org.testng.Assert.assertEquals;

public class DomainObjectComparator {

    public static void assertDeepEqualsWithId(DomainObject actual, DomainObject expected) {
        if (actual.getClass() != expected.getClass()) {
            throw new IllegalArgumentException("Both parameters must be instances of same class");
        }
        if (actual instanceof Message) {
            Message _actual = (Message) actual;
            Message _expected = (Message) expected;
            assertDeepEquals(_actual, _expected);
            assertEquals(_actual.getId(), _expected.getId());
        } else if (actual instanceof Post) {
            Post _actual = (Post) actual;
            Post _expected = (Post) expected;
            assertDeepEquals(_actual, _expected);
            assertEquals(_actual.getId(), _expected.getId());
        } else if (actual instanceof User) {
            User _actual = (User) actual;
            User _expected = (User) expected;
            assertDeepEquals(_actual, _expected);
            assertEquals(_actual.getId(), _expected.getId());
        } else {
            throw new IllegalArgumentException("There is not methods for class " + actual.getClass());
        }
    }

    public static void assertDeepEquals(UserPostMark actual, UserPostMark expected) {
        assertEquals(actual.getUser(), expected.getUser());
        assertEquals(actual.getPost(), expected.getPost());
        assertEquals(actual.isMark(), expected.isMark());
    }

    public static void assertDeepEquals(UserCommentMark actual, UserCommentMark expected) {
        assertEquals(actual.getUser(), expected.getUser());
        assertEquals(actual.getComment(), expected.getComment());
        assertEquals(actual.isMark(), expected.isMark());
    }

    public static void assertDeepEquals(Message actual, Message expected) {
        assertEquals(actual.getSender(), expected.getSender());
        assertEquals(actual.getReceiver(), expected.getReceiver());
        assertEquals(actual.getSubject(), expected.getSubject());
        assertEquals(actual.getText(), expected.getText());
        assertDateEquals(actual.getDate(), expected.getDate());
    }

    public static void assertDeepEquals(Post actual, Post expected) {
        assertEquals(actual.getTitle(), expected.getTitle());
        assertEquals(actual.getText(), expected.getText());
        assertDateEquals(actual.getPostDate(), expected.getPostDate());
    }

    public static void assertDeepEquals(User actual, User expected) {
        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getPass(), expected.getPass());
        assertEquals(actual.getEmail(), expected.getEmail());
        assertEquals(actual.getAvatarPath(), expected.getAvatarPath());
        assertDateEquals(actual.getRegistrationDate(), expected.getRegistrationDate());
    }

    public static void assertDateEquals(Date actual, Date expected) {
        Date _actual = new Date((actual.getTime() / 1000) * 1000);
        Date _expected = new Date((expected.getTime() / 1000) * 1000);

        assertEquals(_actual, _expected);
    }

}
