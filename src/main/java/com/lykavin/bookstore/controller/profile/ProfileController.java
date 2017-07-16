package com.lykavin.bookstore.controller.profile;

import com.lykavin.bookstore.model.UserEntity;
import com.lykavin.bookstore.service.UserPaymentService;
import com.lykavin.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * Created by lykav on 7/16/2017.
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    UserService userService;

    @RequestMapping("")
    public String myProfile(Model model, Principal principal){
        UserEntity user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("userPaymentList", user.getPayments());
        model.addAttribute("userShippingList", user.getShippingAddresses());

//        model.addAttribute("orderList", user.getOrders());

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
}
