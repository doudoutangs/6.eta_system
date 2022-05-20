package com.eta.modules.sys.model;

import lombok.Data;

import javax.persistence.*;
@Data
@Table(name = "sys_menu_role")
public class SysMenuRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "menu_id")
    private Integer menuId;

    @Column(name = "role_id")
    private Integer roleId;
}