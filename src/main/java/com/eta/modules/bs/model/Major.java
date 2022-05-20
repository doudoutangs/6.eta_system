package com.eta.modules.bs.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.eta.core.entity.BaseEntity;
import lombok.Data;

import java.util.Date;
import javax.persistence.*;
@Data
@Table(name = "b_major")
public class Major  extends BaseEntity {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 所属院系
     */
    @Column(name = "universities_id")
    private String universitiesId;
    /**
     * 院系名称
     */
    @Transient
    private String universitiesName;

    /**
     * 专业名称
     */
    private String name;

    /**
     * 专业大类
     */
    private String category;
    @Transient
    private String categoryName;

    /**
     * 专业代码
     */
    private String no;

    /**
     * 专业描述
     */
    private String introduction;

    /**
     * 状态
     */
    private String birth;

    /**
     * 学制
     */
    private Long year;
    private Long sort;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;
    @Override
    public String toString()
    {
        return JSON.toJSONString(this, SerializerFeature.WriteNullStringAsEmpty);
    }
}