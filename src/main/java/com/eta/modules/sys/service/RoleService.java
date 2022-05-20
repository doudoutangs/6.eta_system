package com.eta.modules.sys.service;


import com.eta.core.entity.ProcessResult;
import com.eta.modules.sys.model.SysRole;
import com.eta.modules.sys.vo.SysRoleSelectVo;

import java.util.List;

public interface RoleService {

    List<Integer> findByUserId(Integer id);

    List<SysRole> getAll(SysRole sysRole, String keyword);

    ProcessResult batchDelete(Integer[] ids);

    ProcessResult saveOrUpdate(SysRole sysUser);

    SysRole getById(Integer id);

    ProcessResult deleteById(Integer id);

    List<SysRoleSelectVo> getAllRoles();
}