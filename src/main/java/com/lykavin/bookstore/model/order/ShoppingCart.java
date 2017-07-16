package com.lykavin.bookstore.model.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lykavin.bookstore.model.UserEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by lykav on 7/17/2017.
 */
@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int grandTotal;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JsonIgnore
    private List<CartItem> cartItem;

    @OneToOne(cascade = CascadeType.ALL)
    private UserEntity user;
}
