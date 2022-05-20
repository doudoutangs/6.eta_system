package com.eta.modules.bs.service;

import com.eta.core.entity.ProcessResult;
import com.eta.modules.bs.model.Major;
import com.eta.modules.bs.model.Universities;

import java.util.List;

public interface MajorService {

    List<Major> getAllWithPage(Major param);

    List<Major> getAll();

    List<Major> getChildList(String id);

    ProcessResult saveOrUpdate(Major param);

    Major getById(String id);

    ProcessResult deleteById(String id);

    ProcessResult batchDelete(String[] list);

}
