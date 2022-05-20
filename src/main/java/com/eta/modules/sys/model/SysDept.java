package com.eta.modules.sys.model;

import com.eta.core.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Data
@Table(name = "sys_dept")
public class SysDept   extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dept_name")
    private String deptName;

    @Column(name = "parent_id")
    private Integer parentId;

    private Integer sort;

    private String ancestors; //祖级列表

    private String leader; //负责人

    private String phone; //电话

    private String email; //邮箱

    private Date createTime;//创建时间

    private String createBy;//创建人

    private Date updateTime; //更新时间

    private String updateBy; //更新人

}