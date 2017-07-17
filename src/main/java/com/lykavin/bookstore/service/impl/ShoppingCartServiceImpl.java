package com.lykavin.bookstore.service.impl;

import com.lykavin.bookstore.model.order.CartItem;
import com.lykavin.bookstore.model.order.ShoppingCart;
import com.lykavin.bookstore.repository.ShoppingCartRepository;
import com.lykavin.bookstore.service.CartItemService;
import com.lykavin.bookstore.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lykav on 7/17/2017.
 */
@Transactional
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Override
    public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart) {
        int cartTotal = 0;
        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        for(CartItem cartItem : cartItemList){
            if(cartItem.getBook().getInStockNumber() > 0){
                cartItemService.updateCartItem(cartItem);
                cartTotal += cartItem.getSubtotal();
            }
        }

        shoppingCart.setGrandTotal(cartTotal);
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void clearShoppingCart(ShoppingCart shoppingCart) {
        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        for (CartItem cartItem : cartItemList) {
            cartItem.setShoppingCart(null);
            cartItemService.save(cartItem);
        }

        shoppingCart.setGrandTotal(0);

        shoppingCartRepository.save(shoppingCart);
    }
}
