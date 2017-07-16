package com.lykavin.bookstore.repository;

import com.lykavin.bookstore.model.user.UserShipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lykav on 7/17/2017.
 */
@Repository
public interface UserShippingRepository extends JpaRepository<UserShipping, Long> {

}
