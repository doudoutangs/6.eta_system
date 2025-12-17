package com.eta.modules.sys.model;


import com.eta.core.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
/**
 * @author QQ:553039957
 * 1. gitcode主页： https://gitcode.com/user/tbb414/repos （推荐）
 * 2. github主页：https://github.com/doudoutangs
 * 
 * @Date: 2023/9/25 14:30
 */
@Data
@Table(name = "sys_user")
public class SysUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer postId;

    private String username;

    private String password;

    @Column(name = "real_name")
    private String realName;

    /**
     * 1启用，0停用
     */
    private Integer status;

    @Column(name = "create_time")
    private Date createTime;

    private String email;

    private String phone;

    private Integer sex;

    private String avatar;

    private String city;//城市

    private String birth;

    private String address;

    private String isSysUser;//是否系统用户 0 是 1 否 系统用户不可删除

    private Integer deptId; //部门id

    @Transient
    private Integer ismaster;//0 是管理员  1不是管理员

    @Transient
   /* @ManyToMany(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
    @JoinTable(name = "sys_user_role", joinColumns = { @JoinColumn(name = "id") }, inverseJoinColumns = { @JoinColumn(name = "roleId") })*/
    private Set<SysRole> roles;

    @Transient
    private Integer[] roleIds;
    @Transient
    private Integer roleId;

    @Transient
    private String verify;  //注册时，校验的验证码


}