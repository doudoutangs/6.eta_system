package com.eta.modules.bs.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.eta.core.entity.BaseEntity;
import lombok.Data;

import java.util.Date;
import javax.persistence.*;
@Data
@Table(name = "b_recruit_meeting")
public class RecruitMeeting  extends BaseEntity {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 宣讲主题
     */
    private String title;

    /**
     * 宣讲时间
     */
    @Column(name = "meeting_time")
    private String meetingTime;

    /**
     * 内容
     */
    private String content;

    /**
     * 宣讲公司
     */
    private String company;

    /**
     * 宣讲地点
     */
    private String place;

    /**
     * 状态:0-有效；1-失效；
     */
    private Integer state;

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