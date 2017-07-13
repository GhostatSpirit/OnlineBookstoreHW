package com.lykavin.bookstore.service;

import com.lykavin.bookstore.model.PasswordResetToken;
import com.lykavin.bookstore.model.RoleEntity;
import com.lykavin.bookstore.model.UserEntity;

import java.util.Set;

/**
 * Created by lykav on 2017/6/1.
 */

public interface UserService {
    PasswordResetToken getPasswordResetToken(final String Token);

    void createPasswordResetTokenForUser(final UserEntity user, final String token);

    UserEntity findByUsername(String username);

    UserEntity findByEmail(String email);

    UserEntity createUser(UserEntity user, Set<RoleEntity> userRoles) throws Exception;
}
