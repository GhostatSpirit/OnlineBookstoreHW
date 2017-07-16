package com.lykavin.bookstore.repository;

import com.lykavin.bookstore.model.order.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lykav on 2017/4/23.
 */
@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
//    @Modifying
//    @Transactional
//    @Query("update OrderEntity newOrder " +
//            "set newOrder.userByUid.uid=:qUid, newOrder.bookByBid.bid=:qBid, newOrder.quantity=:qQuantity " +
//            "where newOrder.oid=:qOid")
//    void updateOrder(@Param("qUid") int uid, @Param("qBid") int bid,
//                     @Param("qQuantity") int quantity, @Param("qOid") int oid);

}
