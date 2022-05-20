package com.eta.modules.bs.service;

import com.eta.core.entity.ProcessResult;
import com.eta.modules.bs.model.Major;
import com.eta.modules.bs.model.SchoolClass;

import java.util.List;

public interface SchoolClassService {

    List<SchoolClass> getAllWithPage(SchoolClass param);

    List<SchoolClass> getAll();
    List<SchoolClass> getChildList(String id);

    ProcessResult saveOrUpdate(SchoolClass param);

    SchoolClass getById(String id);

    ProcessResult deleteById(String id);

    ProcessResult batchDelete(String[] list);

}
