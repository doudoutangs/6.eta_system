package com.eta.modules.bs.mapper;

import com.eta.core.mapper.MyMapper;
import com.eta.modules.bs.model.AnalysisReport;

import java.util.List;

public interface AnalysisReportMapper extends MyMapper<AnalysisReport> {
    Integer batchDelete(List<Integer> list);

}