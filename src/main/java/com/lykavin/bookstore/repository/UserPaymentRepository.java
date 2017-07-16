package com.lykavin.bookstore.repository;

import com.lykavin.bookstore.model.user.UserPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lykav on 7/16/2017.
 */
@Repository
public interface UserPaymentRepository extends JpaRepository<UserPayment, Long> {


}
