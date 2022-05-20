package com.eta.modules.sys.model;

import lombok.Data;

import javax.persistence.*;
@Data
@Table(name = "sys_dept_user")
public class SysDeptUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "dept_id")
    private Integer deptId;

    /**
     * 是否是主管 0是 1否
     */
    private Integer ismaster;

}