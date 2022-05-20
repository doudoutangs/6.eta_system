package com.eta.modules.bs.service;

import com.alibaba.fastjson.JSONObject;
import com.eta.modules.bs.model.Stats;
import com.eta.modules.bs.model.StatsReq;
import org.apache.xmlbeans.impl.jam.JSourcePosition;

import java.util.List;

public interface WorkStatsService {
    JSONObject cityStats(List<String> yearList);

    List<Stats> companyNature(StatsReq req);

    List<Stats> universitiesStats(StatsReq req);

    List<Stats> majorStats(Integer universitiesId);

    List<Stats> classStats(Integer majorId);

    JSONObject analyse();

    JSONObject statistics(List<String> yearList);

}
