package com.eta.modules.sys.vo;

import lombok.Data;

import java.util.List;

/**
 *
 *
 *
 **/
@Data
public class SysRoleAuthVo {

    private Integer id;
    private String label;
    private String icon;
    private boolean isOpen=false;
    private List<SysRoleAuthVo> children;

}
