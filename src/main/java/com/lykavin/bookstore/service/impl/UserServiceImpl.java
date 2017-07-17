package com.lykavin.bookstore.service.impl;

import com.lykavin.bookstore.model.PasswordResetToken;
import com.lykavin.bookstore.model.RoleEntity;
import com.lykavin.bookstore.model.UserEntity;
import com.lykavin.bookstore.model.order.ShoppingCart;
import com.lykavin.bookstore.model.user.UserBilling;
import com.lykavin.bookstore.model.user.UserPayment;
import com.lykavin.bookstore.model.user.UserShipping;
import com.lykavin.bookstore.repository.*;
import com.lykavin.bookstore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by lykav on 2017/6/1.
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {

    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserPaymentRepository paymentRepository;
    @Autowired
    private UserShippingRepository shippingRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Override
    public PasswordResetToken getPasswordResetToken(final String token){
        return passwordResetTokenRepository.findByToken(token);
    }
    @Override
    public void createPasswordResetTokenForUser(final UserEntity user, final String token){
        final PasswordResetToken myToken = new PasswordResetToken();
        myToken.setToken(token);
        myToken.setUser(user);
        passwordResetTokenRepository.save(myToken);
    }

    @Override
    public UserEntity findById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public UserEntity findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    public UserEntity findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity createUser(UserEntity user, Set<RoleEntity> userRoles) throws Exception{
        UserEntity localUser = userRepository.findByUsername(user.getUsername());
        if(localUser != null){
            LOG.info("user {} already exists. Will check shopping cart.", user.getName());
            // throw new Exception("user already exists. nothing will be done.");
        } else{
            for(RoleEntity role : userRoles){

                roleRepository.save(role);
            }
            user.setRoles(userRoles);

            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setUser(user);
            user.setShoppingCart(shoppingCart);

            user.setShippingAddresses(new ArrayList<UserShipping>());
            user.setPayments(new ArrayList<UserPayment>());

            localUser = userRepository.save(user);
        }

        return localUser;
    }


    @Override
    public void updateUserBilling(UserBilling userBilling, UserPayment userPayment, UserEntity user) {


        userPayment.setUser(user);
        userPayment.setUserBilling(userBilling);

        // set this new billing info to be default
        // if there is no other cards
        if(user.getPayments().isEmpty()){
            userPayment.setDefaultPayment(true);
        }

        userBilling.setUserPayment(userPayment);

//        if(!user.getPayments().contains(userPayment)) {
//            user.getPayments().add(userPayment);
//        }
//
//        userRepository.save(user);
        paymentRepository.save(userPayment);
    }

    @Override
    public void setUserDefaultPayment(Long defaultPaymentId, UserEntity user) {
        List<UserPayment> userPaymentList = paymentRepository.findAll();

        boolean hasDefault = false;
        for(UserPayment payment : userPaymentList){
            if(payment.getId().equals(defaultPaymentId)){
                hasDefault = true;
                break;
            }
        }

        if(hasDefault) {
            for (UserPayment userPayment : userPaymentList) {
                if (userPayment.getId().equals(defaultPaymentId)) {
                    userPayment.setDefaultPayment(true);
                    paymentRepository.save(userPayment);
                } else {
                    if (userPayment.isDefaultPayment()) {
                        userPayment.setDefaultPayment(false);
                        paymentRepository.save(userPayment);
                    }
                }
            }
        }
    }

    @Override
    public void updateUserShipping(UserShipping userShipping, UserEntity user) {
        userShipping.setUser(user);
        if(user.getShippingAddresses().isEmpty()){
            userShipping.setDefaultShipping(true);
        }
        shippingRepository.save(userShipping);
    }

    @Override
    public void setUserDefaultShipping(Long defaultShippingId, UserEntity user) {
        List<UserShipping> userShippingList = shippingRepository.findAll();

        boolean hasDefault = false;
        for(UserShipping shipping : userShippingList) {
            if(shipping.getId().equals(defaultShippingId)){
                hasDefault = true;
                break;
            }
        }

        if(hasDefault) {
            for (UserShipping shipping : userShippingList) {
                if (shipping.getId().equals(defaultShippingId)) {
                    shipping.setDefaultShipping(true);
                    shippingRepository.save(shipping);
                } else {
                    if (shipping.isDefaultShipping()) {
                        shipping.setDefaultShipping(false);
                        shippingRepository.save(shipping);
                    }
                }
            }
        }
    }

}
