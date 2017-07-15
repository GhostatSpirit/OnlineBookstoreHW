package com.lykavin.bookstore.service.impl;

import com.lykavin.bookstore.model.PasswordResetToken;
import com.lykavin.bookstore.model.RoleEntity;
import com.lykavin.bookstore.model.UserEntity;
import com.lykavin.bookstore.repository.PasswordResetTokenRepository;
import com.lykavin.bookstore.repository.RoleRepository;
import com.lykavin.bookstore.repository.UserRepository;
import com.lykavin.bookstore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by lykav on 2017/6/1.
 */
@Service
public class UserServiceImpl implements UserService {

    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

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
    public UserEntity findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    public UserEntity findByEmail(String username){
        return userRepository.findByEmail(username);
    }

    @Override
    public UserEntity createUser(UserEntity user, Set<RoleEntity> userRoles) throws Exception{
        UserEntity localUser = userRepository.findByUsername(user.getUsername());
        if(localUser != null){
            LOG.info("user {} already exists. nothing will be done.", user.getName());;
            // throw new Exception("user already exists. nothing will be done.");
        } else{
            for(RoleEntity role : userRoles){

                roleRepository.save(role);
            }
            user.setRoles(userRoles);
            localUser = userRepository.save(user);

        }
        return localUser;
    }
}
