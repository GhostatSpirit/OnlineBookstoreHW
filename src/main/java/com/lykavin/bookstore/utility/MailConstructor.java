package com.lykavin.bookstore.utility;

import com.lykavin.bookstore.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Created by lykav on 2017/6/1.
 */
@Component
public class MailConstructor {
    @Autowired
    private Environment env;

    public SimpleMailMessage constructResetTokenEmail(
            String contextPath, Locale locale, String token, UserEntity user, String password
    ){
        String url = contextPath + "/newUser?token=" + token;
        String message = "\nPlease click on this link to verify your email and edit your personal info. Your password is: \n" + password;
        SimpleMailMessage email = new SimpleMailMessage();

        email.setTo(user.getEmail());
        email.setSubject("Yang's Bookstore - New User");
        email.setText(url + message);
       // email.setFrom(env.getProperty("support.email"));
        email.setFrom("ghostatspirit@163.com");
        return email;

    }
}
