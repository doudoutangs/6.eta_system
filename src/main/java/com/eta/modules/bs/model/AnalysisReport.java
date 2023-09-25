package com.eta.modules.bs.model;

import com.eta.core.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "b_analysis_report")
public class AnalysisReport   extends BaseEntity {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 名称
     */
    private String name;
    /**
     * 附件名称
     */
    @Column(name ="file_name")
    private String fileName;

    /**
     * 存储地址
     */
    private String path;

    /**
     * 文件后缀
     */
    @Column(name = "file_suffix")
    private String fileSuffix;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private String updateTime;

    /**
     * 创建人
     */
    @Column(name = "user_id")
    private Integer userId;
    @Transient
    private String userName;

}