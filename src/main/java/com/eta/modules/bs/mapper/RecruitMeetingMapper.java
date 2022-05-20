package com.eta.modules.bs.mapper;

import com.eta.core.mapper.MyMapper;
import com.eta.modules.bs.model.RecruitMeeting;

import java.util.List;

public interface RecruitMeetingMapper extends MyMapper<RecruitMeeting> {
    Integer batchDelete(List<Integer> list);

}