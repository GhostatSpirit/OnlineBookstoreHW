package com.lykavin.bookstore.controller.profile;

import com.lykavin.bookstore.model.UserEntity;
import com.lykavin.bookstore.model.user.UserBilling;
import com.lykavin.bookstore.model.user.UserPayment;
import com.lykavin.bookstore.model.user.UserShipping;
import com.lykavin.bookstore.service.UserPaymentService;
import com.lykavin.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

/**
 * Created by lykav on 7/16/2017.
 */
@Controller
@RequestMapping("/profile")
public class PaymentProfileController {

    @Autowired
    UserService userService;

    @Autowired
    UserPaymentService userPaymentService;

    @RequestMapping("/creditCards")
    public String listOfCreditCards(
            Model model, Principal principal, HttpServletRequest request
    ) {
        UserEntity user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("userPaymentList", user.getPayments());
        model.addAttribute("userShippingList", user.getShippingAddresses());
		/*model.addAttribute("orderList", user.orderList());*/

        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("classActiveBilling", true);
        model.addAttribute("listOfShippingAddresses", true);

        return "/account/myProfile";
    }



    @RequestMapping(value = "/creditCards/add", method = RequestMethod.GET)
    public String addNewCreditCard(
            Model model, Principal principal
    ){
        UserEntity user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);

        model.addAttribute("addNewCreditCard", true);
        model.addAttribute("classActiveBilling", true);
        model.addAttribute("listOfShippingAddresses", true);

        UserBilling userBilling = new UserBilling();
        UserPayment userPayment = new UserPayment();


        model.addAttribute("userBilling", userBilling);
        model.addAttribute("userPayment", userPayment);


        model.addAttribute("userPaymentList", user.getPayments());
        model.addAttribute("userShippingList", user.getShippingAddresses());
		/*model.addAttribute("orderList", user.orderList());*/

        return "/account/myProfile";
    }

    @RequestMapping(value = "/creditCards/update")
    public String updateCreditCard(
            @RequestParam("id") Long creditCardId, Principal principal, Model model
    ){
        UserEntity user = userService.findByUsername(principal.getName());
        UserPayment userPayment = userPaymentService.findById(creditCardId);

        if(user.getId() != userPayment.getUser().getId()){
            // security check failed
            return "badRequestPage";
        } else {
            model.addAttribute("user", user);
            UserBilling userBilling = userPayment.getUserBilling();
            model.addAttribute("userPayment", userPayment);
            model.addAttribute("userBilling", userBilling);

            model.addAttribute("addNewCreditCard", true);
            model.addAttribute("classActiveBilling", true);
            model.addAttribute("listOfShippingAddresses", true);

            model.addAttribute("userPaymentList", user.getPayments());
            model.addAttribute("userShippingList", user.getShippingAddresses());
		/*model.addAttribute("orderList", user.orderList());*/

            return "/account/myProfile";
        }
    }

    @RequestMapping(value = "/creditCards/add", method = RequestMethod.POST)
    public String addNewCreditCardPost(
            @ModelAttribute("userPayment") UserPayment userPayment,
            @ModelAttribute("userBilling") UserBilling userBilling,
            Model model, Principal principal
    ){
        UserEntity user = userService.findByUsername(principal.getName());

        userService.updateUserBilling(userBilling, userPayment, user);


        model.addAttribute("user", user);
        model.addAttribute("userPaymentList", user.getPayments());
        model.addAttribute("userShippingList", user.getShippingAddresses());
        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("classActiveBilling", true);
        model.addAttribute("listOfShippingAddresses", true);

        return "redirect:/profile/creditCards";
    }

    @RequestMapping(value = "/creditCards/remove")
    public String removeCreditCard(
            @ModelAttribute("id") Long creditCardId, Principal principal, Model model
    ){
        UserEntity user = userService.findByUsername(principal.getName());
        UserPayment userPayment = userPaymentService.findById(creditCardId);

        if(user.getId() != userPayment.getUser().getId()){
            // security check failed
            return "badRequestPage";
        } else {
            model.addAttribute("user", user);
            userPaymentService.removeById(creditCardId);

            model.addAttribute("listOfCreditCards", true);
            model.addAttribute("classActiveBilling", true);
            model.addAttribute("listOfShippingAddresses", true);

            model.addAttribute("userPaymentList", user.getPayments());
            model.addAttribute("userShippingList", user.getShippingAddresses());
		/*model.addAttribute("orderList", user.orderList());*/

            return "redirect:/profile/creditCards";
        }
    }

    @RequestMapping(value = "/creditCards/setDefault")
    public String setDefaultPayment(
            @ModelAttribute("defaultUserPaymentId") Long creditCardId, Principal principal, Model model
    ){
        UserEntity user = userService.findByUsername(principal.getName());
        userService.setUserDefaultPayment(creditCardId, user);


        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("classActiveBilling", true);
        model.addAttribute("listOfShippingAddresses", true);

        model.addAttribute("userPaymentList", user.getPayments());
        model.addAttribute("userShippingList", user.getShippingAddresses());
        /*model.addAttribute("orderList", user.orderList());*/

        return "redirect:/profile/creditCards";

    }

    @RequestMapping("/shippingAddresses/add")
    public String addNewShippingAddress(
            Model model, Principal principal
    ){
        UserEntity user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);

        model.addAttribute("addNewShippingAddress", true);
        model.addAttribute("classActiveShipping", true);
        model.addAttribute("listOfCreditCards", true);

        UserShipping userShipping = new UserShipping();

        model.addAttribute("userShipping", userShipping);


        model.addAttribute("userPaymentList", user.getPayments());
        model.addAttribute("userShippingList", user.getShippingAddresses());
		/*model.addAttribute("orderList", user.orderList());*/

        return "/account/myProfile";
    }
}
