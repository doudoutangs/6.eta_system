package com.eta.modules.sys.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Data
@Table(name = "sys_log")
public class SysLog {
    @Id
    @Column(name = "log_id")
    private Integer logId;

    /**
     * debug(10000),info(20000),warn(30000),error(40000)
     */
    @Column(name = "log_level")
    private Integer logLevel;

    private String url;

    @Column(name = "user_id")
    private Integer userId;

    private String username;

    @Column(name = "create_time")
    private Date createTime;

    private String result;

}