package com.lykavin.bookstore.controller.profile;

import com.lykavin.bookstore.model.UserEntity;
import com.lykavin.bookstore.service.UserPaymentService;
import com.lykavin.bookstore.service.UserService;
import com.lykavin.bookstore.service.impl.UserSecurityService;
import com.lykavin.bookstore.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

/**
 * Created by lykav on 7/16/2017.
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {
    String profilePage = "/account/myProfile";
    String redirectProfilePage = "redirect:/profile";

    @Autowired
    UserService userService;
    @Autowired
    UserSecurityService userSecurityService;


    @RequestMapping("")
    public String myProfile(Model model, Principal principal){
        UserEntity user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("userPaymentList", user.getPayments());
        model.addAttribute("userShippingList", user.getShippingAddresses());

        model.addAttribute("orderList", user.getOrders());

//        UserShipping userShipping = new userShipping();
//        model.addAttribute("userShipping", userShipping);

        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("listOfShippingAddresses", true);


//        List<String> stateList = USConstants.listOfUSStatesCode;
//        Collection.sort(stateList);
//        model.addAttribute("stateList", stateList);
        model.addAttribute("classActiveEdit", true);

        return "/account/myProfile";
    }


    @RequestMapping(value="/updateUser", method= RequestMethod.POST)
    public String updateUserInfo(
            @ModelAttribute("user") UserEntity user,
            @ModelAttribute("newPassword") String newPassword,
            Model model
    ) throws Exception {
        UserEntity currentUser = userService.findById(user.getId());

        if(currentUser == null) {
            return "redirect:/login";
            // throw new Exception ("User not found");
        }

		/*check email already exists*/
        if (userService.findByEmail(user.getEmail())!=null) {
            if(userService.findByEmail(user.getEmail()).getId() != currentUser.getId()) {
                model.addAttribute("emailExists", true);
                return profilePage;
            }
        }

		/*check username already exists*/
        if (userService.findByUsername(user.getUsername())!=null) {
            if(userService.findByUsername(user.getUsername()).getId() != currentUser.getId()) {
                model.addAttribute("usernameExists", true);
                return profilePage;
            }
        }

//		update password
        if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")){
            BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
            String dbPassword = currentUser.getPassword();
            if(passwordEncoder.matches(user.getPassword(), dbPassword)){
                currentUser.setPassword(passwordEncoder.encode(newPassword));
            } else {
                model.addAttribute("incorrectPassword", true);

                return profilePage;
            }
        }

        currentUser.setName(user.getName());
        currentUser.setUsername(user.getUsername());
        currentUser.setEmail(user.getEmail());

        userService.save(currentUser);

        model.addAttribute("updateSuccess", true);
        model.addAttribute("user", currentUser);
        model.addAttribute("classActiveEdit", true);

        UserDetails userDetails = userSecurityService.loadUserByUsername(currentUser.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
                userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return redirectProfilePage;
    }
}
