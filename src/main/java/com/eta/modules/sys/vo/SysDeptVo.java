package com.eta.modules.sys.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysDeptVo implements Serializable {
    private Integer id;
    private String label;
    private String icon;
    private boolean spread=true;
    private List<SysDeptVo> children;

}
