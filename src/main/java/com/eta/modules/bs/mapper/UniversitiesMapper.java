package com.eta.modules.bs.mapper;

import com.eta.core.mapper.MyMapper;
import com.eta.modules.bs.model.OrgTree;
import com.eta.modules.bs.model.Universities;

import java.util.List;

public interface UniversitiesMapper extends MyMapper<Universities> {
    Integer batchDelete(List<String> list);

    List<OrgTree> getAllOrg();

}