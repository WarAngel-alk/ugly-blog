package com.my.controller;

import com.my.dao.interfaces.UserDao;
import com.my.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

@Controller
public class UserController {

    private static final String AVATAR_DIRECTORY_PATH = "/recources/images/avatars/";
    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/signup*", method = RequestMethod.GET)
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @RequestMapping(value = "/login*", method = RequestMethod.GET)
    public String showLoginForm(
            @RequestParam(value = "error", required = false) boolean error,
            Model model) {
        if (error) {
            model.addAttribute("errorMessage", "Something is wrong");
        }
        return "login";
    }

    @RequestMapping(value = "/user/{id}*", method = RequestMethod.GET)
    public String showUserPage(@PathVariable("id") long id, Model model) {
        User user = userDao.getUser(id);

        model.addAttribute("user", user);

        return "user";
    }

    @RequestMapping(value = "/user*", method = RequestMethod.PUT)
    public String addUser(
            @ModelAttribute("user") User user,
            Model model) {
        if (user.getAvatarPath() == null || user.getAvatarPath().trim().length() == 0) {
            user.setAvatarPath(null);
        } else {
            try {
                String avatarFilename = saveImageLocal(user.getAvatarPath(),
                        AVATAR_DIRECTORY_PATH, String.valueOf(System.currentTimeMillis()));
            } catch (DownloadAvatarException e1) {
                model.addAttribute("avatarErrorMessage", e1.getMessage());
                return "signup";
            }
        }

        userDao.addUser(user);

        return "redirect:user/" + user.getId();
    }

    private String saveImageLocal(String remotePath, String localDir, String fileName) throws DownloadAvatarException {

        ImageInputStream iis = null;
        try {
            URI uri = new URI(remotePath);
            String format = null;
            ImageReader reader = null;

            iis = ImageIO.createImageInputStream(new File(uri));
            Iterator<ImageReader> readerIterator = ImageIO.getImageReaders(iis);
            if (readerIterator.hasNext()) {
                reader = readerIterator.next();
                format = reader.getFormatName();
            } else {
                throw new DownloadAvatarException("Can not define image type.");
            }

            File saveFile = new File(localDir + fileName + "." + format);

            BufferedImage bufImg = reader.read(0);

            boolean whiteSuccess = ImageIO.write(bufImg, format, saveFile);
            if (!whiteSuccess) {
                throw new DownloadAvatarException("Error while file saving.");
            } else {
                return saveFile.getName();
            }
        } catch (URISyntaxException e1) {
            throw new DownloadAvatarException("Invalid file URL.", e1);
        } catch (IOException e2) {
            throw new DownloadAvatarException("Error while file downloading.", e2);
        } finally {
            if (iis != null) {
                try {
                    iis.close();
                } catch (IOException e) {
                    // Be a bad guy - leave empty catch
                }
            }
        }

    }

    private static class DownloadAvatarException extends Exception {
        public DownloadAvatarException() {
            super();
        }

        public DownloadAvatarException(String message) {
            super(message);
        }

        private DownloadAvatarException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
