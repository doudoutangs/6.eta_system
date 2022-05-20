package com.eta.modules.bs.service;

import com.eta.core.entity.ProcessResult;
import com.eta.modules.bs.model.WorkRegister;

import java.util.List;

public interface WorkRegisterService {

    List<WorkRegister> getAllWithPage(WorkRegister param);

    List<WorkRegister> getAll();

    ProcessResult saveOrUpdate(WorkRegister param);

    WorkRegister getById(Integer id);

    ProcessResult deleteById(Integer id);

    ProcessResult batchDelete(Integer[] list);

}
