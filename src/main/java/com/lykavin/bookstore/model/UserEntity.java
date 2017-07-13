package com.lykavin.bookstore.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by lykav on 2017/4/23.
 */
@Entity
@Table(name = "users", schema = "bookstore", catalog = "")
public class UserEntity {
    private String username;
    private String email;
    private String password;
    private String name;

    private String phone;
    private String address;
    private int uid;
    private Collection<OrderEntity> ordersByUid;

    private Collection<RoleEntity> roles;

    @Basic
    @Column(name = "username", unique = true)
    public String getUsername(){return username;}
    public void setUsername(String username) {
        this.username = username;
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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uid")
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (uid != that.uid) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + uid;
        return result;
    }

    @OneToMany(mappedBy = "userByUid")
    public Collection<OrderEntity> getOrdersByUid() {
        return ordersByUid;
    }

    public void setOrdersByUid(Collection<OrderEntity> ordersByUid) {
        this.ordersByUid = ordersByUid;
    }

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "uid"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Collection<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Collection<RoleEntity> roles) {
        this.roles = roles;
    }


//    @Override
//    @Transient
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Set<GrantedAuthority> authorities = new HashSet<>();
//        userRoles.forEach(ur -> authorities.add(new Authority(ur.getRole().getName())));
//        return null;
//    }
//
//    @Override
//    @Transient
//    public String getUsername() {
//        // assume that email is the username
//        return email;
//    }
//
//    @Override
//    @Transient
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    @Transient
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    @Transient
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    @Transient
//    public boolean isEnabled() {
//        return this.enabled;
//    }



}
