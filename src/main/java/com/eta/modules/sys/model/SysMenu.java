package com.eta.modules.sys.model;


import com.eta.core.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
@Data
@Table(name = "sys_menu")
public class SysMenu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 图标
     */
    private String icon;

    /**
     * 菜单名称
     */
    @Column(name = "menu_name")
    private String menuName;

    /**
     * 路径
     */
    private String url;

    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 排序号
     */
    private Byte sort;

    /**
     * 是否显示  0显示 1不显示
     */
    @Column(name = "is_show")
    private Byte isShow;

    private String permission;

    private String menuType;

    private String isSysMenu;//是否是系统菜单 0 是  1不是  系统菜单不可删除

    private String isLink; //是否外链

    @Transient
    private boolean open=true;

}