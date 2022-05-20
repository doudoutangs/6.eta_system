package com.eta.modules.sys.service;

import com.eta.core.entity.ProcessResult;
import com.eta.modules.sys.model.SysDict;

import java.util.List;

/**
 *
 *
 *
 **/
public interface DictService {

    List<SysDict> getAllWithPage(SysDict dict);

    ProcessResult saveOrUpdate(SysDict dict);

    SysDict getById(Integer id);

    ProcessResult deleteById(Integer id);

    ProcessResult batchDelete(Integer[] list);

    List<SysDict> getAllDict();

    List<SysDict>listRootType();

    List<SysDict> getDictByDictValue(String dictValue);

}
