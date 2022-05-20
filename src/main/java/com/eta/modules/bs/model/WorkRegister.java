package com.eta.modules.bs.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.eta.core.entity.BaseEntity;
import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "b_work_register")
public class WorkRegister extends BaseEntity {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 学号
     */
    @Column(name = "student_id")
    private Integer studentId;
    @Transient
    private String studentName;

    /**
     * 工作单位
     */
    private String company;

    /**
     * 工作城市
     */
    @Column(name = "work_city")
    private String workCity;

    @Transient
    private String workCityName;
    /**
     * 工作地点
     */
    @Column(name = "work_address")
    private String workAddress;

    /**
     * 单位性质
     */
    @Column(name = "company_nature")
    private String companyNature;
    @Transient
    private String companyNatureName;

    /**
     * 工资
     */
    private String salary;

    /**
     * 专业符合度
     */
    @Column(name = "fit_degree")
    private String fitDegree;

    /**
     * 登记年份
     */
    @Column(name = "register_year")
    private String registerYear;
    /**
     * 登记日期
     */
    @Column(name = "register_date")
    private String registerDate;

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