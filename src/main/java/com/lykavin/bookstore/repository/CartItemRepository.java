package com.lykavin.bookstore.repository;

import com.lykavin.bookstore.model.order.CartItem;
import com.lykavin.bookstore.model.order.OrderEntity;
import com.lykavin.bookstore.model.order.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by lykav on 7/17/2017.
 */
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
    List<CartItem> findByOrder(OrderEntity order);
}
