package com.eta.modules.sys.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;

/**
 *
 *
 *
 **/
@Data
public class SysRoleSelectVo {

    private String name;
    private Integer value;
    @Override
    public String toString()
    {
        return JSON.toJSONString(this, SerializerFeature.WriteNullStringAsEmpty);
    }
}
