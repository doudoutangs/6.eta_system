package com.eta.modules.bs.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.eta.core.entity.BaseEntity;
import lombok.Data;

import java.util.Date;
import javax.persistence.*;
@Data
@Table(name = "b_message_board")
public class MessageBoard  extends BaseEntity {
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
     * 日期
     */
    @Column(name = "release_date")
    private String releaseDate;

    /**
     * 内容
     */
    private String content;

    /**
     * 反馈人
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 发布人姓名
     */
    @Transient
    private String userName;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 状态:0-已提交；1-已阅读；2-已处理；
     */
    private Integer state;

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