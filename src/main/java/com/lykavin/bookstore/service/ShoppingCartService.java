package com.lykavin.bookstore.service;

import com.lykavin.bookstore.model.order.ShoppingCart;

/**
 * Created by lykav on 7/17/2017.
 */
public interface ShoppingCartService {
    ShoppingCart updateShoppingCart(ShoppingCart shoppingCart);

    void clearShoppingCart(ShoppingCart shoppingCart);

}
