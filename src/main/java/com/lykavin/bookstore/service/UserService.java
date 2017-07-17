package com.lykavin.bookstore.service;

import com.lykavin.bookstore.model.PasswordResetToken;
import com.lykavin.bookstore.model.RoleEntity;
import com.lykavin.bookstore.model.UserEntity;
import com.lykavin.bookstore.model.user.UserBilling;
import com.lykavin.bookstore.model.user.UserPayment;
import com.lykavin.bookstore.model.user.UserShipping;

import java.util.Set;

/**
 * Created by lykav on 2017/6/1.
 */

public interface UserService {
    PasswordResetToken getPasswordResetToken(final String Token);

    void createPasswordResetTokenForUser(final UserEntity user, final String token);

    UserEntity findById(Long id);
    UserEntity save(UserEntity user);

    UserEntity findByUsername(String username);

    UserEntity findByEmail(String email);

    UserEntity createUser(UserEntity user, Set<RoleEntity> userRoles) throws Exception;

    void updateUserBilling(UserBilling userBilling, UserPayment userPayment, UserEntity user);
    void setUserDefaultPayment(Long defaultPaymentId, UserEntity user);

    void updateUserShipping(UserShipping userShipping, UserEntity user);
    void setUserDefaultShipping(Long defaultShippingId, UserEntity user);
}
