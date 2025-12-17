package com.eta.modules.bs.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * @author QQ:553039957
 * 1. gitcode主页： https://gitcode.com/user/tbb414/repos （推荐）
 * 2. github主页：https://github.com/doudoutangs
 * 
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
