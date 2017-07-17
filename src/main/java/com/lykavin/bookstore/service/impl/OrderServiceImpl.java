package com.lykavin.bookstore.service.impl;

import com.lykavin.bookstore.model.order.BillingAddress;
import com.lykavin.bookstore.model.order.Payment;
import com.lykavin.bookstore.model.order.ShippingAddress;
import com.lykavin.bookstore.model.user.UserBilling;
import com.lykavin.bookstore.model.user.UserPayment;
import com.lykavin.bookstore.model.user.UserShipping;
import com.lykavin.bookstore.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * Created by lykav on 7/17/2017.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public BillingAddress setByUserBilling(UserBilling userBilling, BillingAddress billingAddress) {
        billingAddress.setStreet(userBilling.getStreet());
        billingAddress.setName(userBilling.getName());
        billingAddress.setCity(userBilling.getCity());
        billingAddress.setState(userBilling.getState());
        billingAddress.setCountry(userBilling.getCountry());
        billingAddress.setZipcode(userBilling.getZipcode());

        return billingAddress;
    }

    @Override
    public ShippingAddress setByUserShipping(UserShipping userShipping, ShippingAddress shippingAddress) {
        shippingAddress.setName(userShipping.getName());
        shippingAddress.setStreet(userShipping.getStreet());
        shippingAddress.setCity(userShipping.getCity());
        shippingAddress.setState(userShipping.getState());
        shippingAddress.setCountry(userShipping.getCountry());
        shippingAddress.setZipcode(userShipping.getZipcode());

        return shippingAddress;
    }

    @Override
    public Payment setByUserPayment(UserPayment userPayment, Payment payment) {
        payment.setType(userPayment.getType());
        payment.setHolderName(userPayment.getHolderName());
        payment.setCardName(userPayment.getCardName());
        payment.setCardNumber(userPayment.getCardNumber());
        payment.setExpiryMonth(userPayment.getExpiryMonth());
        payment.setExpiryYear(userPayment.getExpiryYear());
        payment.setCvc(userPayment.getCvc());

        return payment;
    }



}
