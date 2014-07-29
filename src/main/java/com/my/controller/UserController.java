package com.my.controller;

import com.my.dao.interfaces.UserDao;
import com.my.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

@Controller
public class UserController {

    private static final String AVATAR_DIRECTORY_PATH = "/resources/images/avatars/";

    @Autowired
    private UserDao userDao;

    @Autowired
    private ServletContext servletContext;

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
            @ModelAttribute("user") @Valid User user, BindingResult bindResult,
            Model model) {
        if (bindResult.hasErrors()) {
            return "signup";
        }

        if (user.getAvatarPath() == null || user.getAvatarPath().trim().length() == 0) {
            user.setAvatarPath(null);
        } else {
            try {
                String avatarFilename = saveImageLocal(user.getAvatarPath(),
                        servletContext.getRealPath(AVATAR_DIRECTORY_PATH), String.valueOf(System.currentTimeMillis()));
                user.setAvatarPath(avatarFilename);
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
            URL url = new URL(remotePath);
            String format = null;
            ImageReader reader = null;

//            InputStream is = new Ur
            iis = ImageIO.createImageInputStream(url.openStream());
            Iterator<ImageReader> readerIterator = ImageIO.getImageReaders(iis);
            if (readerIterator.hasNext()) {
                reader = readerIterator.next();
                format = reader.getFormatName();
            } else {
                throw new DownloadAvatarException("Can not define image type.");
            }

            File saveFile = new File(localDir + System.getProperty("file.separator") + fileName + "." + format);

            BufferedImage bufImg = ImageIO.read(iis);

            boolean whiteSuccess = ImageIO.write(bufImg, format, saveFile);
            if (!whiteSuccess) {
                throw new DownloadAvatarException("Error while file saving.");
            } else {
                return saveFile.getName();
            }
        } catch (MalformedURLException e1) {
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
