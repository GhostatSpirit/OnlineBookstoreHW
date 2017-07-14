package com.lykavin.bookstore.repository;

import com.lykavin.bookstore.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lykav on 2017/6/1.
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    String userRoleName = "ROLE_USER";
    String adminRoleName = "ROLE_ADMIN";


    RoleEntity findByName(String name);
}
