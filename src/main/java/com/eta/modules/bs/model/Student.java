package com.eta.modules.bs.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.eta.core.entity.BaseEntity;
import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "b_student")
public class Student extends BaseEntity {
    /**
     * 主键
     */
    @Id
    private Integer id;

    /**
     * 班级id
     */
    @Column(name = "class_id")
    private String classId;
    /**
     * 班级名称
     */
    @Transient
    private String className;
    /**
     * 专业id
     */
    @Column(name = "major_id")
    private String majorId;
    /**
     * 学院id
     */
    @Column(name = "universities_id")
    private String universitiesId;
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
     * 学号
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
     * 状态:0-在读；1-休学；2-毕业
     */
    private String state;

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
    public String toString() {
        return JSON.toJSONString(this, SerializerFeature.WriteNullStringAsEmpty);
    }
}