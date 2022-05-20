package com.eta.modules.bs.mapper;

import com.eta.core.mapper.MyMapper;
import com.eta.modules.bs.model.Major;
import com.eta.modules.sys.model.SysDept;

import java.util.List;

public interface MajorMapper extends MyMapper<Major> {
    List<Major> getChildList(String id);
    Integer batchDelete(List<String> list);

}