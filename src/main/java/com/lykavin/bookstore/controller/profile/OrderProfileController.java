package com.lykavin.bookstore.controller.profile;

import com.lykavin.bookstore.model.UserEntity;
import com.lykavin.bookstore.model.order.CartItem;
import com.lykavin.bookstore.model.order.OrderEntity;
import com.lykavin.bookstore.model.user.UserShipping;
import com.lykavin.bookstore.service.CartItemService;
import com.lykavin.bookstore.service.OrderService;
import com.lykavin.bookstore.service.UserService;
import com.lykavin.bookstore.service.impl.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/profile/order")
public class OrderProfileController {

    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;
    @Autowired
    CartItemService cartItemService;

    @RequestMapping("")
    public String orderDetail(
            @RequestParam(value = "id", required = false) Long orderId,
            Principal principal, Model model
    ){
        UserEntity user = userService.findByUsername(principal.getName());
        OrderEntity order = orderService.findOne(orderId);

        if(order.getUser().getId()!=user.getId()) {
            return "badRequestPage";
        } else {
            List<CartItem> cartItemList = cartItemService.findByOrder(order);
            model.addAttribute("cartItemList", cartItemList);
            model.addAttribute("user", user);
            model.addAttribute("order", order);

            model.addAttribute("userPaymentList", user.getPayments());
            model.addAttribute("userShippingList", user.getShippingAddresses());
            model.addAttribute("orderList", user.getOrders());

            UserShipping userShipping = new UserShipping();
            model.addAttribute("userShipping", userShipping);

            model.addAttribute("listOfShippingAddresses", true);
            model.addAttribute("classActiveOrders", true);
            model.addAttribute("listOfCreditCards", true);
            model.addAttribute("displayOrderDetail", true);

            return "/account/myProfile";
        }
    }
}
