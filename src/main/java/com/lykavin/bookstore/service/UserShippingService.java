package com.lykavin.bookstore.service;

import com.lykavin.bookstore.model.user.UserShipping;
import org.springframework.stereotype.Service;

/**
 * Created by lykav on 7/17/2017.
 */
public interface UserShippingService {

    UserShipping findById(Long id);

    void removeById(Long id);

}
