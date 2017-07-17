package com.lykavin.bookstore.controller;

import com.lykavin.bookstore.model.UserEntity;
import com.lykavin.bookstore.model.order.*;
import com.lykavin.bookstore.model.user.UserPayment;
import com.lykavin.bookstore.model.user.UserShipping;
import com.lykavin.bookstore.service.CartItemService;
import com.lykavin.bookstore.service.OrderService;
import com.lykavin.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

/**
 * Created by lykav on 7/17/2017.
 */
@Controller
public class CheckoutController {

    @Autowired
    UserService userService;
    @Autowired
    CartItemService cartItemService;
    @Autowired
    OrderService orderService;


    @RequestMapping("/checkout")
    public String checkout(
            @RequestParam("id") Long cartId,
            @RequestParam(value="missingRequiredField", required=false) boolean missingRequiredField,
            Model model, Principal principal
    ){
        UserEntity user = userService.findByUsername(principal.getName());

        if(cartId != user.getShoppingCart().getId()) {
            return "badRequestPage";
        }

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());

        if(cartItemList.size() == 0) {
            model.addAttribute("emptyCart", true);
            return "forward:/cart";
        }

        for (CartItem cartItem : cartItemList) {
            if(cartItem.getBook().getInStockNumber() < cartItem.getQty()) {
                model.addAttribute("notEnoughStock", true);
                return "forward:/cart";
            }
        }

        List<UserShipping> userShippingList = user.getShippingAddresses();
        List<UserPayment> userPaymentList = user.getPayments();

        model.addAttribute("userShippingList", userShippingList);
        model.addAttribute("userPaymentList", userPaymentList);

        if (userPaymentList.size() == 0) {
            model.addAttribute("emptyPaymentList", true);
        } else {
            model.addAttribute("emptyPaymentList", false);
        }

        if (userShippingList.size() == 0) {
            model.addAttribute("emptyShippingList", true);
        } else {
            model.addAttribute("emptyShippingList", false);
        }

        ShoppingCart shoppingCart = user.getShoppingCart();

        ShippingAddress shippingAddress = new ShippingAddress();
        Payment payment = new Payment();
        BillingAddress billingAddress = new BillingAddress();

        for(UserShipping userShipping : userShippingList) {
            if(userShipping.isDefaultShipping()) {
                orderService.setByUserShipping(userShipping, shippingAddress);
            }
        }

        for (UserPayment userPayment : userPaymentList) {
            if(userPayment.isDefaultPayment()) {
                orderService.setByUserPayment(userPayment, payment);
                orderService.setByUserBilling(userPayment.getUserBilling(), billingAddress);
            }
        }

        model.addAttribute("shippingAddress", shippingAddress);
        model.addAttribute("payment", payment);
        model.addAttribute("billingAddress", billingAddress);
        model.addAttribute("cartItemList", cartItemList);
        model.addAttribute("shoppingCart", user.getShoppingCart());

        model.addAttribute("classActiveShipping", true);

        if(missingRequiredField) {
            model.addAttribute("missingRequiredField", true);
        }

        return "checkout";

    }
}
