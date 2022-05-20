package com.eta.modules.sys.mapper;

import com.eta.core.mapper.MyMapper;
import com.eta.modules.sys.model.SysPost;

import java.util.List;

public interface SysPostMapper extends MyMapper<SysPost> {
    Integer batchDelete(List<Integer> list);

}