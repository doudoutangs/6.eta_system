package com.eta.modules.bs.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;


@Data
public class StatsReq {
    String year;
    @Override
    public String toString()
    {
        return JSON.toJSONString(this, SerializerFeature.WriteNullStringAsEmpty);
    }
}
