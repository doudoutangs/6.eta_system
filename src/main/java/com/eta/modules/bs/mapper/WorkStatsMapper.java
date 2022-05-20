package com.eta.modules.bs.mapper;

import com.eta.core.mapper.MyMapper;
import com.eta.modules.bs.model.Stats;
import com.eta.modules.bs.model.StatsReq;

import java.util.List;

public interface WorkStatsMapper extends MyMapper<Stats> {
    List<Stats> cityStats(StatsReq req);

    List<Stats> companyNature(StatsReq req);

    List<Stats> universitiesStats(StatsReq req);

    List<Stats> majorStats(Integer universitiesId);

    List<Stats> classStats(Integer majorId);

}