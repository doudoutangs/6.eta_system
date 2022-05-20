package com.eta.modules.sys.vo;


import lombok.Data;

import java.util.List;

@Data
public class SysMenuSelectVo{
    private Integer id;
    private String name;
    private boolean open;
    private List<SysMenuSelectVo> children;

}
