package com.lykavin.bookstore.service.impl;

import com.lykavin.bookstore.model.BookEntity;
import com.lykavin.bookstore.model.UserEntity;
import com.lykavin.bookstore.model.order.CartItem;
import com.lykavin.bookstore.model.order.ShoppingCart;
import com.lykavin.bookstore.repository.CartItemRepository;
import com.lykavin.bookstore.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.util.List;

/**
 * Created by lykav on 7/17/2017.
 */
@Service
@Transactional
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    CartItemRepository cartItemRepository;

    @Override
    public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart) {
        return cartItemRepository.findByShoppingCart(shoppingCart);
    }

    @Override
    public CartItem updateCartItem(CartItem cartItem) {
        int subtotal = cartItem.getBook().getOurPrice() * cartItem.getQty();
        cartItem.setSubtotal(subtotal);
        cartItemRepository.save(cartItem);
        return cartItem;
    }

    @Override
    public CartItem addBookToCartItem(BookEntity book, UserEntity user, int qty) {
        List<CartItem> cartItemList = findByShoppingCart(user.getShoppingCart());

        for(CartItem cartItem : cartItemList){
            if(book.getId() == cartItem.getBook().getId()){
                cartItem.setQty(cartItem.getQty() + qty);
                return updateCartItem(cartItem);
            }
        }

        // could not find book in existing cart items, creating new one
        CartItem cartItem = new CartItem();
        cartItem.setShoppingCart(user.getShoppingCart());
        cartItem.setBook(book);
        cartItem.setQty(qty);

        cartItem = updateCartItem(cartItem);

        return cartItem;
    }

    @Override
    public CartItem findById(Long id) {
        return cartItemRepository.findOne(id);
    }

    @Override
    public void removeCartItem(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }
}
