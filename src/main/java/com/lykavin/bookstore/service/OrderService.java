package com.lykavin.bookstore.service;

import com.lykavin.bookstore.model.order.BillingAddress;
import com.lykavin.bookstore.model.order.Payment;
import com.lykavin.bookstore.model.order.ShippingAddress;
import com.lykavin.bookstore.model.user.UserBilling;
import com.lykavin.bookstore.model.user.UserPayment;
import com.lykavin.bookstore.model.user.UserShipping;

/**
 * Created by lykav on 7/17/2017.
 */
public interface OrderService {
    BillingAddress setByUserBilling(UserBilling userBilling, BillingAddress billingAddress);
    ShippingAddress setByUserShipping(UserShipping userShipping, ShippingAddress shippingAddress);
    Payment setByUserPayment(UserPayment userPayment, Payment payment);
}
