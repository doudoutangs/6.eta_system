package com.eta.modules.sys.model;

import lombok.Data;

import javax.persistence.*;
@Data
@Table(name = "sys_user_role")
public class SysUserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "role_id")
    private Integer roleId;

}