package com.eta.modules.sys.vo;

import lombok.Data;

import java.util.List;

/**
 *
 *
 *
 **/
@Data
public class SysDeptSelectVo {

    private Integer id;
    private String name;
    private boolean open;
    private List<SysDeptSelectVo> children;

}
