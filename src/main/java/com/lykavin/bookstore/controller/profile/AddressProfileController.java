package com.lykavin.bookstore.controller.profile;

import com.lykavin.bookstore.model.UserEntity;
import com.lykavin.bookstore.model.user.UserShipping;
import com.lykavin.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created by lykav on 7/16/2017.
 */
@Controller
@RequestMapping("/profile/addresses")
public class AddressProfileController {

    @Autowired
    UserService userService;

    @RequestMapping("")
    public String listOfShippingAddresses(
            Model model, Principal principal, HttpServletRequest request
    ) {
        UserEntity user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("userPaymentList", user.getPayments());
        model.addAttribute("userShippingList", user.getShippingAddresses());
		/*model.addAttribute("orderList", user.orderList());*/

        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("classActiveShipping", true);
        model.addAttribute("listOfShippingAddresses", true);

        return "/account/myProfile";
    }

    @RequestMapping("/add")
    public String addNewShippingAddress(
            Model model, Principal principal
    ){
        UserEntity user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);

        model.addAttribute("addNewShippingAddress", true);
        model.addAttribute("classActiveShipping", true);

        UserShipping userShipping = new UserShipping();
        model.addAttribute("userShipping", userShipping);

        return "/account/myProfile";
    }
}
