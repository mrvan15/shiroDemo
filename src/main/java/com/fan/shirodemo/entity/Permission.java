package com.fan.shirodemo.entity;

import javax.persistence.*;
import javax.print.DocFlavor;
import java.io.Serializable;

/**
 * 权限表
 */
@Entity
@Table(name = "permission")
public class Permission implements Serializable {
    private static final long serialVersionUID = 5043991674897079269L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String permission;

    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
