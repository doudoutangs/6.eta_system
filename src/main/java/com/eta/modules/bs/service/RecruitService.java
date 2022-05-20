package com.eta.modules.bs.service;

import com.eta.core.entity.ProcessResult;
import com.eta.modules.bs.model.Recruit;

import java.util.List;

public interface RecruitService {

    List<Recruit> getAllWithPage(Recruit param);

    List<Recruit> getAll();

    ProcessResult saveOrUpdate(Recruit param);

    Recruit getById(Integer id);

    ProcessResult deleteById(Integer id);

    ProcessResult batchDelete(Integer[] list);

}
