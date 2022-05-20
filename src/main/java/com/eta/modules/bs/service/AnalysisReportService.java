package com.eta.modules.bs.service;

import com.eta.core.entity.ProcessResult;
import com.eta.modules.bs.model.AnalysisReport;

import java.util.List;

/**
 *
 **/
public interface AnalysisReportService {

    List<AnalysisReport> getAllWithPage(AnalysisReport param);

    List<AnalysisReport> getAll();

    ProcessResult saveOrUpdate(AnalysisReport param);

    AnalysisReport getById(Integer id);

    ProcessResult deleteById(Integer id);

    ProcessResult batchDelete(Integer[] list);


}
