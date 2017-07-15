package com.lykavin.bookstore.model;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

/**
 * Created by lykav on 2017/4/23.
 */
@Entity
@Table(name = "books", schema = "bookstore", catalog = "")
public class BookEntity {

    private long id;

    private String title;
    private String author;
    private String publisher;

    private String description;

    private int listPrice;
    private int ourPrice;
    private int inStockNumber;

    private String publicationDate;
    private String language;
    private String category;

    private int numberOfPages;
    private String format;
    private String isbn;

    private boolean active = true;


    private MultipartFile bookImage;


    private Collection<OrderEntity> orders;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid")
    public long getId() {
        return id;
    }

    public void setId(long bid) {
        this.id = bid;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Column(name = "description", columnDefinition = "text")
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

    @Transient
    public MultipartFile getBookImage() {
        return bookImage;
    }

    public void setBookImage(MultipartFile bookImage) {
        this.bookImage = bookImage;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getListPrice() {
        return listPrice;
    }

    @Column(nullable = true)
    public void setListPrice(int listPrice) {
        this.listPrice = listPrice;
    }

    public int getOurPrice() {
        return ourPrice;
    }

    @Column(nullable = true)
    public void setOurPrice(int ourPrice) {
        this.ourPrice = ourPrice;
    }

    public int getInStockNumber() {
        return inStockNumber;
    }

    @Column(nullable = true)
    public void setInStockNumber(int inStockNumber) {
        this.inStockNumber = inStockNumber;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    @Column(nullable = true)
    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    // The length of ISBN-13 should be 14 (with a '-')
    @Column(length = 15)
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    @OneToMany(mappedBy = "bookByBid")
    public Collection<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(Collection<OrderEntity> orders) {
        this.orders = orders;
    }
}
