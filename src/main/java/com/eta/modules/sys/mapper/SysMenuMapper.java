package com.eta.modules.sys.mapper;


import com.eta.core.mapper.MyMapper;
import com.eta.modules.sys.model.SysMenu;

import java.util.List;

public interface SysMenuMapper extends MyMapper<SysMenu> {
    List<SysMenu> getTopList();

    List<SysMenu> getChildDeptList(Integer id);

    List<SysMenu> getSelMenuPermission(Integer roleId);

    List<Integer> selectMenuListByRoleId(Integer roleId);

    List<SysMenu> selectAllMenu();

    List<SysMenu> getOperationMenuTop();
    Integer batchDelete(List<Integer> list);

}