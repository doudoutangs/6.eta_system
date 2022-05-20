package com.eta.modules.bs.mapper;

import com.eta.core.mapper.MyMapper;
import com.eta.modules.bs.model.Recruit;

import java.util.List;

public interface RecruitMapper extends MyMapper<Recruit> {
    Integer batchDelete(List<Integer> list);

}