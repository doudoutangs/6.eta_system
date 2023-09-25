package com.eta.modules.sys.model;


import com.eta.core.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
/**
 * @author 553039957@qq.com
 * 1. gitcode主页： https://gitcode.net/tbb414 （推荐）
 * 2. github主页：https://github.com/doudoutangs
 * 3. gitee(码云)主页：https://gitee.com/spdoudoutang
 * @Date: 2023/9/25 14:30
 */
@Data
@Table(name = "sys_deal_log")
public class SysDealLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户操作
     */
    private String operation;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 执行时长(毫秒)
     */
    private Long time;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

}