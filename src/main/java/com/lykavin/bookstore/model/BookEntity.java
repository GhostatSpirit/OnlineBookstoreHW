package com.lykavin.bookstore.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

/**
 * Created by lykav on 2017/4/23.
 */
@Entity
@Table(name = "books", schema = "bookstore", catalog = "")
public class BookEntity {
    private String title;
    private BigDecimal price;
    private String description;
    private String author;
    private String img;
    private int bid;
    private Collection<OrderEntity> ordersByBid;

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "author")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Basic
    @Column(name = "img")
    public String getImg() { return img ; }

    public void setImg(String img) { this.img = img; }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bid")
    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookEntity that = (BookEntity) o;

        if (bid != that.bid) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (author != null ? !author.equals(that.author) : that.author != null) return false;
        if (img != null ? !img.equals(that.img) : that.img != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (img != null ? img.hashCode() : 0);
        result = 31 * result + bid;
        return result;
    }

    @OneToMany(mappedBy = "bookByBid")
    public Collection<OrderEntity> getOrdersByBid() {
        return ordersByBid;
    }

    public void setOrdersByBid(Collection<OrderEntity> ordersByBid) {
        this.ordersByBid = ordersByBid;
    }
}
