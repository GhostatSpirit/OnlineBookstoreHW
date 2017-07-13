package com.lykavin.bookstore.repository;

import com.lykavin.bookstore.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lykav on 2017/4/23.
 */
@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
    @Modifying
    @Transactional
    @Query("update OrderEntity newOrder " +
            "set newOrder.userByUid.uid=:qUid, newOrder.bookByBid.bid=:qBid, newOrder.quantity=:qQuantity " +
            "where newOrder.oid=:qOid")
    void updateOrder(@Param("qUid") int uid, @Param("qBid") int bid,
                     @Param("qQuantity") int quantity, @Param("qOid") int oid);

}
