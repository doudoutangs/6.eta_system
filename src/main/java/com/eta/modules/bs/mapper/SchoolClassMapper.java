package com.eta.modules.bs.mapper;

import com.eta.core.mapper.MyMapper;
import com.eta.modules.bs.model.Major;
import com.eta.modules.bs.model.SchoolClass;

import java.util.List;

public interface SchoolClassMapper extends MyMapper<SchoolClass> {
    List<SchoolClass> getChildList(String id);
    Integer batchDelete(List<String> list);

}