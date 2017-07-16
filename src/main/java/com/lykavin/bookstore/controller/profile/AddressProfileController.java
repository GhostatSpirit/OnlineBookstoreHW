package com.lykavin.bookstore.controller.profile;

import com.lykavin.bookstore.model.UserEntity;
import com.lykavin.bookstore.model.user.UserShipping;
import com.lykavin.bookstore.service.UserService;
import com.lykavin.bookstore.service.UserShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    @Autowired
    UserShippingService userShippingService;

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

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addNewShippingAddressPost(
            @ModelAttribute("userShipping") UserShipping userShipping,
            Model model, Principal principal
    ){
        UserEntity user = userService.findByUsername(principal.getName());

        userService.updateUserShipping(userShipping, user);

        return "redirect:/profile/addresses";
    }

    @RequestMapping(value = "/update")
    public String updateShippingAddress(
            @RequestParam("id") Long shippingId, Principal principal, Model model
    ){
        UserEntity user = userService.findByUsername(principal.getName());
        UserShipping userShipping = userShippingService.findById(shippingId);

        if(user.getId() != userShipping.getUser().getId()){
            // security check failed
            return "badRequestPage";
        } else {
            model.addAttribute("user", user);
            model.addAttribute("userShipping", userShipping);

            model.addAttribute("addNewShippingAddress", true);
            model.addAttribute("classActiveShipping", true);

            model.addAttribute("userPaymentList", user.getPayments());
            model.addAttribute("userShippingList", user.getShippingAddresses());
		/*model.addAttribute("orderList", user.orderList());*/

            return "/account/myProfile";
        }
    }

    @RequestMapping(value = "/remove")
    public String removeCreditCard(
            @ModelAttribute("id") Long shippingId, Principal principal, Model model
    ){
        UserEntity user = userService.findByUsername(principal.getName());
        UserShipping userShipping = userShippingService.findById(shippingId);

        if(user.getId() != userShipping.getUser().getId()){
            // security check failed
            return "badRequestPage";
        } else {
            model.addAttribute("user", user);
            userShippingService.removeById(shippingId);

            return "redirect:/profile/addresses";
        }
    }


    @RequestMapping(value = "/setDefault")
    public String setDefaultPayment(
            @ModelAttribute("defaultShippingAddressId") Long shippingId, Principal principal, Model model
    ){
        UserEntity user = userService.findByUsername(principal.getName());
        userService.setUserDefaultShipping(shippingId, user);

        return "redirect:/profile/addresses";

    }


}
