package com.lykavin.bookstore.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by lykav on 2017/5/31.
 */
@Entity
@Table(name = "roles", schema = "bookstore", catalog = "")
public class RoleEntity {

    private int roleId;
    private String name;


    private Collection<UserEntity> users;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "roleName", nullable = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "roles")
    public Collection<UserEntity> getUsers() {
        return users;
    }
    public void setUsers(Collection<UserEntity> users) {
        this.users = users;
    }
}
