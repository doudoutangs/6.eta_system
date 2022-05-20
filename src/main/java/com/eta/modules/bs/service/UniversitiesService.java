package com.eta.modules.bs.service;

import com.eta.core.entity.ProcessResult;
import com.eta.modules.bs.model.OrgTree;
import com.eta.modules.bs.model.Universities;
import com.eta.modules.sys.model.SysDict;

import java.util.List;

/**
 *
 **/
public interface UniversitiesService {

    List<Universities> getAllWithPage(Universities param);

    List<Universities> getAll();

    ProcessResult saveOrUpdate(Universities param);

    Universities getById(String id);

    ProcessResult deleteById(String id);

    ProcessResult batchDelete(String[] list);

    List<OrgTree> getAllOrg();

}
