package com.eta.modules.bs.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * @author 553039957@qq.com
 * 1. gitcode主页： https://gitcode.net/tbb414 （推荐）
 * 2. github主页：https://github.com/doudoutangs
 * 3. gitee(码云)主页：https://gitee.com/spdoudoutang
 */
@Data
public class StatisVO {
    private String name;
    private Long total;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
