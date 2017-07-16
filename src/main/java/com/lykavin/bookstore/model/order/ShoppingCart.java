package com.lykavin.bookstore.model.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lykavin.bookstore.model.UserEntity;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REMOVE;

/**
 * Created by lykav on 7/17/2017.
 */
@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int grandTotal;

    @OneToMany(mappedBy = "shoppingCart",
            cascade ={CascadeType.ALL},
            fetch=FetchType.LAZY)
    @JsonIgnore
    private List<CartItem> cartItem;

    @OneToOne(cascade = CascadeType.ALL)
    private UserEntity user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(int grandTotal) {
        this.grandTotal = grandTotal;
    }

    public List<CartItem> getCartItem() {
        return cartItem;
    }

    public void setCartItem(List<CartItem> cartItem) {
        this.cartItem = cartItem;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
