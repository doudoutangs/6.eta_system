package com.eta.modules.bs.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.eta.core.entity.BaseEntity;
import lombok.Data;

import java.util.Date;
import javax.persistence.*;
@Data
@Table(name = "b_teacher")
public class Teacher  extends BaseEntity {
    /**
     * 主键
     */
    @Id
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 生日
     */
    private String birth;
    /**
     * 籍贯
     */
    private String city;
    /**
     * 工号
     */
    private String no;
    /**
     * 身份证号
     */
    @Column(name = "id_no")
    private String idNo;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 等级
     */
    private String level;

    /**
     * 入职日期
     */
    @Column(name = "entry_date")
    private String entryDate;

    /**
     * 职位
     */
    private String job;

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