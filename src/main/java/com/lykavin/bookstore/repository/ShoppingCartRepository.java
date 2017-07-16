package com.lykavin.bookstore.repository;

import com.lykavin.bookstore.model.order.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lykav on 7/17/2017.
 */
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}
