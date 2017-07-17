package com.lykavin.bookstore.service.impl;

import com.lykavin.bookstore.model.BookEntity;
import com.lykavin.bookstore.model.UserEntity;
import com.lykavin.bookstore.model.order.*;
import com.lykavin.bookstore.model.user.UserBilling;
import com.lykavin.bookstore.model.user.UserPayment;
import com.lykavin.bookstore.model.user.UserShipping;
import com.lykavin.bookstore.repository.BookRepository;
import com.lykavin.bookstore.repository.OrderRepository;
import com.lykavin.bookstore.service.CartItemService;
import com.lykavin.bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

/**
 * Created by lykav on 7/17/2017.
 */
@Transactional
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    BookRepository bookRepository;

    @Override
    public OrderEntity createOrder(
            ShoppingCart shoppingCart,
            ShippingAddress shippingAddress,
            BillingAddress billingAddress,
            Payment payment,
            String shippingMethod,
            UserEntity user
    ){
        OrderEntity order = new OrderEntity();

        billingAddress.setPayment(payment);
        payment.setBillingAddress(billingAddress);

        order.setOrderStatus("created");
        order.setPayment(payment);
        order.setShippingAddress(shippingAddress);
        order.setShippingMethod(shippingMethod);

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        for(CartItem cartItem : cartItemList) {
            BookEntity book = cartItem.getBook();
            cartItem.setOrder(order);
            book.setInStockNumber(book.getInStockNumber() - cartItem.getQty());
            bookRepository.save(book);
        }

        order.setCartItems(cartItemList);
        order.setOrderDate(Calendar.getInstance().getTime());
        order.setOrderTotal(shoppingCart.getGrandTotal());
        shippingAddress.setOrder(order);

        payment.setOrder(order);
        order.setUser(user);
        order = orderRepository.save(order);

        return order;
    }

    @Override
    public OrderEntity findOne(Long id) {
        return orderRepository.findOne(id);
    }

    @Override
    public BillingAddress setByUserBilling(UserBilling userBilling, BillingAddress billingAddress) {
        billingAddress.setName(userBilling.getName());
        billingAddress.setStreet(userBilling.getStreet());
        billingAddress.setCity(userBilling.getCity());
        billingAddress.setState(userBilling.getState());
        billingAddress.setCountry(userBilling.getCountry());
        billingAddress.setZipcode(userBilling.getZipcode());

        return billingAddress;
    }

    @Override
    public BillingAddress setByShippingAddress(ShippingAddress shippingAddress, BillingAddress billingAddress) {

        billingAddress.setName(shippingAddress.getName());
        billingAddress.setStreet(shippingAddress.getStreet());
        billingAddress.setCity(shippingAddress.getCity());
        billingAddress.setState(shippingAddress.getState());
        billingAddress.setCountry(shippingAddress.getCountry());
        billingAddress.setZipcode(shippingAddress.getZipcode());

        return billingAddress;
    }


    @Override
    public ShippingAddress setByUserShipping(UserShipping userShipping, ShippingAddress shippingAddress) {
        shippingAddress.setName(userShipping.getName());
        shippingAddress.setStreet(userShipping.getStreet());
        shippingAddress.setCity(userShipping.getCity());
        shippingAddress.setState(userShipping.getState());
        shippingAddress.setCountry(userShipping.getCountry());
        shippingAddress.setZipcode(userShipping.getZipcode());

        return shippingAddress;
    }

    @Override
    public Payment setByUserPayment(UserPayment userPayment, Payment payment) {
        payment.setType(userPayment.getType());
        payment.setHolderName(userPayment.getHolderName());
        payment.setCardName(userPayment.getCardName());
        payment.setCardNumber(userPayment.getCardNumber());
        payment.setExpiryMonth(userPayment.getExpiryMonth());
        payment.setExpiryYear(userPayment.getExpiryYear());
        payment.setCvc(userPayment.getCvc());

        return payment;
    }

    @Override
    public boolean isOrderValid(ShippingAddress shippingAddress, Payment payment, BillingAddress billingAddress) {
        return !(
            shippingAddress.getStreet().isEmpty() || shippingAddress.getCity().isEmpty()
            || shippingAddress.getStreet().isEmpty()
            || shippingAddress.getName().isEmpty()
            || shippingAddress.getZipcode().isEmpty() || payment.getCardNumber().isEmpty()
            || payment.getCvc() == 0 || billingAddress.getStreet().isEmpty()
            || billingAddress.getCity().isEmpty() || billingAddress.getState().isEmpty()
            || billingAddress.getName().isEmpty()
            || billingAddress.getZipcode().isEmpty()
        );
    }


}
