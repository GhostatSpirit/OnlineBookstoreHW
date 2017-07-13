package com.lykavin.bookstore.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.sql.Timestamp;

/**
 * Created by lykav on 2017/4/23.
 */
@Entity
@Table(name = "orders", schema = "bookstore", catalog = "")
public class OrderEntity {
    @Min(1)
    private Integer quantity;
    private Timestamp orderTime;
    private int oid;

    private UserEntity userByUid;
    private BookEntity bookByBid;

    @Basic
    @Column(name = "quantity")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "order_time")
    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "oid")
    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderEntity that = (OrderEntity) o;

        if (oid != that.oid) return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        if (orderTime != null ? !orderTime.equals(that.orderTime) : that.orderTime != null) return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result = quantity != null ? quantity.hashCode() : 0;
        result = 31 * result + (orderTime != null ? orderTime.hashCode() : 0);
        result = 31 * result + oid;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    public UserEntity getUserByUid() {
        return userByUid;
    }

    public void setUserByUid(UserEntity userByUid) {
        this.userByUid = userByUid;
    }

    @ManyToOne
    @JoinColumn(name = "bid", referencedColumnName = "bid")
    public BookEntity getBookByBid() {
        return bookByBid;
    }

    public void setBookByBid(BookEntity bookByBid) {
        this.bookByBid = bookByBid;
    }
}
