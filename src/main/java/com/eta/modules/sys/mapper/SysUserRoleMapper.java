package com.eta.modules.sys.mapper;


import com.eta.core.mapper.MyMapper;
import com.eta.modules.sys.model.SysUserRole;

import java.util.List;

public interface SysUserRoleMapper extends MyMapper<SysUserRole> {
    Integer batchDelete(List<Integer> list);

}