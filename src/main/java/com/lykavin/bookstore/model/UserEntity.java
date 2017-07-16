package com.lykavin.bookstore.model;

import com.lykavin.bookstore.model.order.OrderEntity;
import com.lykavin.bookstore.model.user.UserPayment;
import com.lykavin.bookstore.model.user.UserShipping;
import com.lykavin.bookstore.security.Authority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lykav on 2017/4/23.
 */
@Entity
@Table(name = "users", schema = "bookstore", catalog = "")
public class UserEntity implements UserDetails {
    private long id;

    private String username;
    private String password;

    private String name;

    private String email;
    private String phone;
    private String address;

    private Collection<OrderEntity> orders;
    private Collection<RoleEntity> roles;

    private boolean enabled=true;


    private List<UserShipping> shippingAddresses;

    private List<UserPayment> payments;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long getId() {
        return id;
    }
    public void setId(long uid) {
        this.id = uid;
    }

    @Basic
    @Column(name = "username", unique = true)
    public String getUsername(){return username;}
    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "name", nullable = true)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "phone", nullable = true)
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "address", nullable = true)
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }



    @OneToMany(mappedBy = "userByUid")
    public Collection<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(Collection<OrderEntity> orders) {
        this.orders = orders;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    public List<UserShipping> getShippingAddresses() {
        return shippingAddresses;
    }

    public void setShippingAddresses(List<UserShipping> shippingAddresses) {
        this.shippingAddresses = shippingAddresses;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    public List<UserPayment> getPayments() {
        return payments;
    }

    public void setPayments(List<UserPayment> payments) {
        this.payments = payments;
    }

    @ManyToMany
    @JoinTable(name = "userRole", joinColumns = @JoinColumn(name = "uid"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Collection<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Collection<RoleEntity> roles) {
        this.roles = roles;
    }



    // methods for UserDetails
    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        roles.forEach(ur -> authorities.add(new Authority(ur.getName())));
        return null;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return this.enabled;
    }



}
