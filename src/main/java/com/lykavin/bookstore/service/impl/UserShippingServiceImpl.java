package com.lykavin.bookstore.service.impl;

import com.lykavin.bookstore.model.UserEntity;
import com.lykavin.bookstore.model.user.UserShipping;
import com.lykavin.bookstore.repository.UserShippingRepository;
import com.lykavin.bookstore.service.UserShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lykav on 7/17/2017.
 */
@Transactional
@Service
public class UserShippingServiceImpl implements UserShippingService {

    @Autowired
    UserShippingRepository shippingRepository;

    @Override
    public UserShipping findById(Long id) {
        return shippingRepository.findOne(id);
    }

    @Override
    public void removeById(Long id) {
        UserShipping targetShipping = shippingRepository.findOne(id);
        boolean isOldDefault = targetShipping.isDefaultShipping();

        shippingRepository.delete(id);
        shippingRepository.flush();

        if(isOldDefault) {
            UserEntity user = targetShipping.getUser();
            for(UserShipping shipping : user.getShippingAddresses()){
                if(!shipping.isDefaultShipping()) {
                    shipping.setDefaultShipping(true);
                    shippingRepository.save(shipping);
                    break;
                }
            }
        }
    }
}
