package com.lykavin.bookstore.service.impl;

import com.lykavin.bookstore.model.UserEntity;
import com.lykavin.bookstore.model.user.UserPayment;
import com.lykavin.bookstore.repository.UserPaymentRepository;
import com.lykavin.bookstore.service.UserPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lykav on 7/16/2017.
 */
@Service
public class UserPaymentServiceImpl implements UserPaymentService {

    @Autowired
    UserPaymentRepository userPaymentRepository;

    @Override
    public UserPayment findById(Long id) {
        return userPaymentRepository.findOne(id);
    }

    @Override
    public void removeById(Long id) {
        UserPayment targetPayment = userPaymentRepository.findOne(id);
        boolean isOldDefault = targetPayment.isDefaultPayment();

        userPaymentRepository.delete(id);
        userPaymentRepository.flush();

        if(isOldDefault) {
            UserEntity user = targetPayment.getUser();
            for(UserPayment tempPayment : user.getPayments()){
               if(!tempPayment.isDefaultPayment()) {
                   tempPayment.setDefaultPayment(true);
                   userPaymentRepository.save(tempPayment);
                   break;
               }
            }
        }

    }
}
