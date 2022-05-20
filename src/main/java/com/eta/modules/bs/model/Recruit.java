package com.eta.modules.bs.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.eta.core.entity.BaseEntity;
import lombok.Data;

import java.util.Date;
import javax.persistence.*;
@Data
@Table(name = "b_recruit")
public class Recruit  extends BaseEntity {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 主键
     */
    private String title;

    /**
     * 发布日期
     */
    @Column(name = "release_date")
    private String releaseDate;

    /**
     * 内容
     */
    private String content;

    /**
     * 发布人
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 状态:0-有效；1-失效
     */
    private Integer state;

    /**
     * 招聘企业
     */
    private String company;

    /**
     * 企业规模
     */
    private String scale;

    /**
     * 企业类型
     */
    @Column(name = "company_type")
    private String companyType;

    /**
     * 所属行业
     */
    private String industry;

    /**
     * 工作地点
     */
    @Column(name = "duty_station")
    private String dutyStation;

    /**
     * 企业邮箱
     */
    private String email;

    /**
     * 招聘人数
     */
    @Column(name = "recruit_count")
    private Integer recruitCount;

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