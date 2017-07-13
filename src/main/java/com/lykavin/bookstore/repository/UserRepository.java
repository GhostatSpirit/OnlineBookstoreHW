package com.lykavin.bookstore.repository;

import com.lykavin.bookstore.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lykav on 201a7/4/23.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);

    // define a updateUser() method
    @Modifying
    @Transactional
    @Query("update UserEntity us " +
            "set us.email=:qEmail, us.password=:qPassword, " +
                "us.name=:qName, us.phone=:qPhone, us.address=:qAddress " +
            "where us.uid=:qUid")
    void updateUser(@Param("qEmail") String email, @Param("qPassword") String password,
                    @Param("qName") String name, @Param("qPhone") String phone,
                    @Param("qAddress") String address, @Param("qUid") Integer uid);
}
