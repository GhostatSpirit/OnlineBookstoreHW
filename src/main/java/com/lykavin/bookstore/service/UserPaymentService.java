package com.lykavin.bookstore.service;

import com.lykavin.bookstore.model.user.UserPayment;

/**
 * Created by lykav on 7/16/2017.
 */
public interface UserPaymentService {

    UserPayment findById(Long id);

    void removeById(Long id);
}
