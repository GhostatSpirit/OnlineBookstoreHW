package com.lykavin.bookstore.service;

import com.lykavin.bookstore.model.UserEntity;
import com.lykavin.bookstore.model.order.*;
import com.lykavin.bookstore.model.user.UserBilling;
import com.lykavin.bookstore.model.user.UserPayment;
import com.lykavin.bookstore.model.user.UserShipping;

/**
 * Created by lykav on 7/17/2017.
 */
public interface OrderService {
    BillingAddress setByUserBilling(UserBilling userBilling, BillingAddress billingAddress);
    BillingAddress setByShippingAddress(ShippingAddress shippingAddress, BillingAddress billingAddress);

    ShippingAddress setByUserShipping(UserShipping userShipping, ShippingAddress shippingAddress);
    Payment setByUserPayment(UserPayment userPayment, Payment payment);

    public boolean isOrderValid(ShippingAddress shippingAddress, Payment payment, BillingAddress billingAddress);

    OrderEntity createOrder(
            ShoppingCart shoppingCart,
            ShippingAddress shippingAddress,
            BillingAddress billingAddress,
            Payment payment,
            String shippingMethod,
            UserEntity user
    );

    OrderEntity findOne(Long id);
}
