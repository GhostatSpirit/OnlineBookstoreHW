package com.lykavin.bookstore.controller;

import com.lykavin.bookstore.model.PasswordResetToken;
import com.lykavin.bookstore.model.RoleEntity;
import com.lykavin.bookstore.model.UserEntity;
import com.lykavin.bookstore.service.UserService;
import com.lykavin.bookstore.utility.MailConstructor;
import com.lykavin.bookstore.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;

/**
 * Created by lykav on 2017/6/1.
 */
@Controller
public class AccountController {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailConstructor mailConstructor;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userSecurityService;


    private String prefix = "/account/";

    @RequestMapping("/myAccount")
    public String myAccount(){
        return prefix + "myAccount";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLogin(Model model){
        model.addAttribute("classActiveLogin", true);
        return prefix + "myAccount";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String postLogin(Model model){
        model.addAttribute("classActiveLogin", true);
        return prefix + "myAccount";
    }

    @RequestMapping("/forgetPassword")
    public String forgetPassword(

            Model model){

        model.addAttribute("classActiveForgetPassword", true);
        return prefix + "myAccount";

    }

    @RequestMapping(value="/newUser", method= RequestMethod.POST)
    public String newUserPost(
            HttpServletRequest request,
            @ModelAttribute("email") String userEmail,
            @ModelAttribute("username") String username,
            Model model
            ) throws Exception{
        model.addAttribute("classActiveNewAccount", true);
        model.addAttribute("email", userEmail);
        model.addAttribute("username", username);

        if(userService.findByUsername(username) != null){
            model.addAttribute("usernameExists", true);
            return prefix + "myAccount";
        }

        if(userService.findByEmail(userEmail) != null){
            model.addAttribute("emailExists", true);
            return prefix + "myAccount";
        }

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setEmail(userEmail);

        String password = SecurityUtility.randomPassword();
        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        user.setPassword(encryptedPassword);

        RoleEntity role = new RoleEntity();
        role.setRoleId(1);
        role.setName("ROLE_USER");
        Set<RoleEntity> userRoles = new HashSet<>();
        userRoles.add(role);

        userService.createUser(user, userRoles);

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);

        String appUrl = "http://" + request.getServerName() + ":" +
                request.getServerPort() + request.getContextPath();
        SimpleMailMessage email =
                mailConstructor.constructResetTokenEmail(
                        appUrl, request.getLocale(), token, user, password
                );
        mailSender.send(email);

        model.addAttribute("emailSent", "true");
        model.addAttribute("newUser", user);

        return prefix + "myAccount";

    }

    @RequestMapping(value="/newUser", method= RequestMethod.GET)
    public String newUser(
            Locale locale,
            @RequestParam("token") String token,
            Model model){
        PasswordResetToken passToken = userService.getPasswordResetToken(token);

        if(passToken == null){
            String message = "Invalid Token.";
            model.addAttribute("message", message);
            return "redirect:/badRequest";
        }

        UserEntity user =  passToken.getUser();
        String username = user.getUsername();

        UserDetails userDetails = userSecurityService.loadUserByUsername(username);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, userDetails.getPassword(),
                userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        model.addAttribute("user", user);
        model.addAttribute("classActiveEdit", true);
        return prefix + "myProfile";
    }


}
