package com.my.tiles;

import com.my.dao.interfaces.UserDao;
import com.my.model.Message;
import com.my.model.User;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.preparer.ViewPreparer;
import org.apache.tiles.request.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class HeaderPreparer implements ViewPreparer {

    @Autowired
    private UserDao userDao;

    @Override
    public void execute(Request tilesContext, AttributeContext attributeContext) {
        // TODO: replace checking user authentication with security annotations (which?)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getPrincipal().equals("guest")) {
            String currentUserName = auth.getName();
            User currentUser = userDao.getUser(currentUserName);
            List<Message> usersMessages = currentUser.getIncomingMessages();

            for (Message usersMsg : usersMessages) {
                if (!usersMsg.isRead()) {
                    tilesContext.getContext("request").put("headerMailboxRelativeUrl", "/resources/images/header_mail_new.png");
                    return;
                }
            }
            tilesContext.getContext("request").put("headerMailboxRelativeUrl", "/resources/images/header_mail.png");
        }
    }
}
