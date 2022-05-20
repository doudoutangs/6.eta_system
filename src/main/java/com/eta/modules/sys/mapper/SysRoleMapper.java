package com.eta.modules.sys.mapper;


import com.eta.core.mapper.MyMapper;
import com.eta.modules.sys.model.SysRole;

import java.util.List;

public interface SysRoleMapper extends MyMapper<SysRole> {
    List<SysRole> findByUserId(Integer userId);
    Integer batchDelete(List<Integer> list);

}