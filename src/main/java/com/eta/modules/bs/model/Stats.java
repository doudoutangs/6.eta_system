package com.eta.modules.bs.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;
/**
 * @author QQ:553039957
 * 1. gitcode主页： https://gitcode.com/user/tbb414/repos （推荐）
 * 2. github主页：https://github.com/doudoutangs
 * 
 */

@Data
public class Stats {
    String year;
    String name;
    String value;
    @Override
    public String toString()
    {
        return JSON.toJSONString(this, SerializerFeature.WriteNullStringAsEmpty);
    }
}
