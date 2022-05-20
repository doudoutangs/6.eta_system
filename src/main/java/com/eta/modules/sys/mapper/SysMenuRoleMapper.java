package com.eta.modules.sys.mapper;


import com.eta.core.mapper.MyMapper;
import com.eta.modules.sys.model.SysMenuRole;

import java.util.List;

public interface SysMenuRoleMapper extends MyMapper<SysMenuRole> {
    int deleteByRoleId(Integer roleId);
    Integer batchDelete(List<Integer> list);

}