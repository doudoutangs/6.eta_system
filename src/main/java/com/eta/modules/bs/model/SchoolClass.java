package com.eta.modules.bs.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.eta.core.entity.BaseEntity;
import lombok.Data;

import java.util.Date;
import javax.persistence.*;
@Data
@Table(name = "b_class")
public class SchoolClass  extends BaseEntity {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 专业id
     */
    @Column(name = "major_id")
    private String majorId;
    /**
     * 专业名称
     */
    @Transient
    private String majorName;

    /**
     * 专业名称
     */
    private String name;

    /**
     * 开始日期
     */
    @Column(name = "start_date")
    private String startDate;

    /**
     * 结束日期
     */
    @Column(name = "end_date")
    private String endDate;

    /**
     * 班主任
     */
    @Column(name = "teacher_id")
    private Integer teacherId;
    @Transient
    private String teacherName;

    /**
     * 编号
     */
    private String no;
    private Integer sort;
    /**
     * 编号
     */
    private String term;
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