package com.lykavin.bookstore.controller;

import com.lykavin.bookstore.model.UserEntity;
import com.lykavin.bookstore.model.order.*;
import com.lykavin.bookstore.model.user.UserPayment;
import com.lykavin.bookstore.model.user.UserShipping;
import com.lykavin.bookstore.repository.UserPaymentRepository;
import com.lykavin.bookstore.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by lykav on 7/17/2017.
 */
@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    UserService userService;
    @Autowired
    CartItemService cartItemService;
    @Autowired
    OrderService orderService;
    @Autowired
    UserShippingService userShippingService;
    @Autowired
    UserPaymentService userPaymentService;
    @Autowired
    ShoppingCartService shoppingCartService;


    @RequestMapping(value="", method = RequestMethod.GET)
    public String checkout(
            @RequestParam(value = "id", required = false) Long cartId,
            @RequestParam(value="missingRequiredField", required=false) boolean missingRequiredField,
            Model model, Principal principal
    ){
        UserEntity user = userService.findByUsername(principal.getName());

//        if(cartId != user.getShoppingCart().getId()) {
//            return "badRequestPage";
//        }

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

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String checkoutPost(@ModelAttribute("shippingAddress") ShippingAddress shippingAddress,
                               @ModelAttribute("billingAddress") BillingAddress billingAddress, @ModelAttribute("payment") Payment payment,
                               @ModelAttribute("billingSameAsShipping") String billingSameAsShipping,
                               @ModelAttribute("shippingMethod") String shippingMethod, Principal principal, Model model) {
        ShoppingCart shoppingCart = userService.findByUsername(principal.getName()).getShoppingCart();

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
        model.addAttribute("cartItemList", cartItemList);


        if (billingSameAsShipping.equals("true")) {
            billingAddress = orderService.setByShippingAddress(shippingAddress, billingAddress);
        }

        if (!orderService.isOrderValid(shippingAddress, payment, billingAddress))
            return "redirect:/checkout?id=" + shoppingCart.getId() + "&missingRequiredField=true";

        UserEntity user = userService.findByUsername(principal.getName());

        OrderEntity order = orderService.createOrder(shoppingCart, shippingAddress, billingAddress, payment, shippingMethod, user);

        shoppingCartService.clearShoppingCart(shoppingCart);

        LocalDate today = LocalDate.now();
        LocalDate estimatedDeliveryDate;

        if (shippingMethod.equals("groundShipping")) {
            estimatedDeliveryDate = today.plusDays(5);
        } else {
            estimatedDeliveryDate = today.plusDays(3);
        }

        model.addAttribute("estimatedDeliveryDate", estimatedDeliveryDate);

        return "orderSubmittedPage";
    }

    @RequestMapping(value = "/shipping", method = RequestMethod.GET)
    public ResponseEntity<ShippingAddress> getShippingAddress(
            @RequestParam("userShippingId") Long userShippingId,
            Principal principal
    ){
        UserEntity user = userService.findByUsername(principal.getName());
        UserShipping userShipping = userShippingService.findById(userShippingId);

        if(userShipping == null){
            return new ResponseEntity<ShippingAddress>(HttpStatus.NOT_FOUND);
        } else if(user.getId() != userShipping.getUser().getId()){
            return new ResponseEntity<ShippingAddress>(HttpStatus.FORBIDDEN);
        } else{
            ShippingAddress shippingAddress = new ShippingAddress();
            orderService.setByUserShipping(userShipping, shippingAddress);

            return new ResponseEntity<ShippingAddress>(shippingAddress, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/payment", method = RequestMethod.GET)
    public ResponseEntity<Payment> getPayment(
            @RequestParam("userPaymentId") Long userPaymentId,
            Principal principal
    ){
        UserEntity user = userService.findByUsername(principal.getName());
        UserPayment userPayment = userPaymentService.findById(userPaymentId);

        if(userPayment == null){
            return new ResponseEntity<Payment>(HttpStatus.NOT_FOUND);
        } else if(user.getId() != userPayment.getUser().getId()){
            return new ResponseEntity<Payment>(HttpStatus.FORBIDDEN);
        } else{
            Payment payment = new Payment();
            orderService.setByUserPayment(userPayment, payment);

            return new ResponseEntity<Payment>(payment, HttpStatus.OK);
        }
    }
}
