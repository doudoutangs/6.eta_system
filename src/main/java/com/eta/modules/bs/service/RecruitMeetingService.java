package com.eta.modules.bs.service;

import com.eta.core.entity.ProcessResult;
import com.eta.modules.bs.model.RecruitMeeting;

import java.util.List;

public interface RecruitMeetingService {

    List<RecruitMeeting> getAllWithPage(RecruitMeeting param);

    List<RecruitMeeting> getAll();

    ProcessResult saveOrUpdate(RecruitMeeting param);

    RecruitMeeting getById(Integer id);

    ProcessResult deleteById(Integer id);

    ProcessResult batchDelete(Integer[] list);

}
