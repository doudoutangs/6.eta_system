package com.eta.modules.bs.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;
/**
 * @author QQ:553039957
 * 1. gitcode主页： https://gitcode.net/tbb414 （推荐）
 * 2. github主页：https://github.com/doudoutangs
 * 3. gitee(码云)主页：https://gitee.com/spdoudoutang
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
