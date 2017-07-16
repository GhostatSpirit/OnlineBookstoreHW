package com.lykavin.bookstore.controller;

import com.lykavin.bookstore.model.BookEntity;
import com.lykavin.bookstore.model.UserEntity;
import com.lykavin.bookstore.model.order.CartItem;
import com.lykavin.bookstore.model.order.ShoppingCart;
import com.lykavin.bookstore.service.BookService;
import com.lykavin.bookstore.service.CartItemService;
import com.lykavin.bookstore.service.ShoppingCartService;
import com.lykavin.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

/**
 * Created by lykav on 7/17/2017.
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private BookService bookService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @RequestMapping("")
    public String shoppingCart(Model model, Principal princpal){
        UserEntity user = userService.findByUsername(princpal.getName());
        ShoppingCart shoppingCart = user.getShoppingCart();

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
        shoppingCartService.updateShoppingCart(shoppingCart);

        model.addAttribute("cartItemList", cartItemList);
        model.addAttribute("shoppingCart", shoppingCart);

        return "shoppingCart";
    }

    @RequestMapping("/add")
    public String addItem(@ModelAttribute("book")BookEntity book,
                          @ModelAttribute("qty") String qtyString,
                          Model model, Principal principal){
        UserEntity user = userService.findByUsername(principal.getName());
        book = bookService.findOne(book.getId());

        int qty = Integer.parseInt(qtyString);
        if(qty > book.getInStockNumber()){
            model.addAttribute("notEnoughStock", true);
            return "forward:/bookDetail?id="+book.getId();
        }

        CartItem cartItem = cartItemService.addBookToCartItem(book, user, qty);
        model.addAttribute("addBookSuccess", true);

        return "forward:/bookDetail?id="+book.getId();
    }

    @RequestMapping("/update")
    public String updateShoppingCart(@ModelAttribute("id") Long cartItemId,
                                     @ModelAttribute("qty") int qty){
        CartItem cartItem = cartItemService.findById(cartItemId);
        cartItem.setQty(qty);
        cartItemService.updateCartItem(cartItem);

        return "forward:/cart";
    }

    @RequestMapping("/remove")
    public String removeItem(@RequestParam("id") Long cartItemId){
        cartItemService.removeCartItem(cartItemService.findById(cartItemId));
        return "forward:/cart";
    }
}
