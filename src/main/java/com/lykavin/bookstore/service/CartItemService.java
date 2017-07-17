package com.lykavin.bookstore.service;

import com.lykavin.bookstore.model.BookEntity;
import com.lykavin.bookstore.model.UserEntity;
import com.lykavin.bookstore.model.order.CartItem;
import com.lykavin.bookstore.model.order.OrderEntity;
import com.lykavin.bookstore.model.order.ShoppingCart;

import java.awt.print.Book;
import java.util.List;

/**
 * Created by lykav on 7/17/2017.
 */
public interface CartItemService {
    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);

    CartItem updateCartItem(CartItem cartItem);

    CartItem addBookToCartItem(BookEntity book, UserEntity user, int qty);

    CartItem findById(Long id);

    void removeCartItem(CartItem cartItem);

    CartItem save(CartItem cartItem);

    List<CartItem> findByOrder(OrderEntity order);
}
