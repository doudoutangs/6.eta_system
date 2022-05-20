package com.eta.modules.sys.service;

import com.eta.core.entity.ProcessResult;
import com.eta.modules.sys.model.SysDept;
import com.eta.modules.sys.vo.SysDeptSelectVo;
import com.eta.modules.sys.vo.SysDeptVo;

import java.util.List;

public interface DeptService {

    List<SysDept> getAll();

    List<SysDeptVo> treeList();

    List<SysDept> getChildDeptList(Integer id);

    List<SysDeptSelectVo> treeSelectList();

    SysDept getDeptById(Integer id);

    ProcessResult saveOrUpdate(SysDept sysDept) throws Exception;

    ProcessResult deleteById(Integer id);

}