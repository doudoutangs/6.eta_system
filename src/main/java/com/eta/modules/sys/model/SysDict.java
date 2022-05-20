package com.eta.modules.sys.model;

import com.eta.core.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Table(name = "sys_dict")
@Data
public class SysDict extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 数据字典名称
     */
    @Column(name = "dict_name")
    private String dictName;

    /**
     * 数据字典值
     */
    @Column(name = "dict_value")
    private String dictValue;

    /**
     * 数据字典类型
     */
    @Column(name = "dict_level")
    private String dictLevel;

    /**
     * 状态（0正常 1停用）
     */
    private String status;

    /**
     * 父级ID
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 排序号
     */
    @Column(name = "dict_sort")
    private Integer dictSort;

    @Column(name = "create_time")
    private Date createTime;


}