package com.lykavin.bookstore.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lykavin.bookstore.model.UserEntity;

import javax.persistence.*;

/**
 * Created by lykav on 7/16/2017.
 */
@Entity
public class UserShipping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipcode;

    private boolean defaultShipping;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isDefaultShipping() {
        return defaultShipping;
    }

    public void setDefaultShipping(boolean defaultShipping) {
        this.defaultShipping = defaultShipping;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserShipping that = (UserShipping) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
