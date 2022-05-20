package com.eta.modules.sys.model;

import com.eta.core.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Data
@Table(name = "sys_post")
public class SysPost  extends BaseEntity {
    /**
     * 岗位ID
     */
    @Id
    @Column(name = "post_id")
    private Integer postId;

    /**
     * 岗位编码
     */
    @Column(name = "post_code")
    private String postCode;

    /**
     * 岗位名称
     */
    @Column(name = "post_name")
    private String postName;

    /**
     * 显示顺序
     */
    @Column(name = "post_sort")
    private Integer postSort;

    /**
     * 状态（0正常 1停用）
     */
    private String status;

    /**
     * 创建者
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新者
     */
    @Column(name = "update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

}