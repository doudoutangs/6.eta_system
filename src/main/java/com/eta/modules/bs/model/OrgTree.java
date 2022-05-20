package com.eta.modules.bs.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.eta.modules.sys.vo.SysMenuSelectVo;
import lombok.Data;

import java.util.List;

/**
 * 学院，专业，班级组织结构
 * Created by sugar on 2022/4/11.
 */
@Data
public class OrgTree {
    private boolean open;
    private String id;
    private String name;
    private String code;
    private String parentId;
    private List<OrgTree> children;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
