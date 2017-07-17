package com.lykavin.bookstore.repository;

import com.lykavin.bookstore.model.order.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lykav on 2017/4/23.
 */
@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {


}
