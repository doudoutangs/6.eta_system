package com.eta.modules.sys.service;


import com.eta.core.entity.ProcessResult;
import com.eta.modules.sys.model.SysUser;

import java.util.List;

public interface UserService {
    List<SysUser> getAll(SysUser sysUser, String keyword);

    List<SysUser> getByDeptId(Integer deptId);

    SysUser getById(Integer id);

    ProcessResult deleteById(Integer id);

    ProcessResult save(SysUser sysUser);

    SysUser getUser(SysUser sysUser);

//    ProcessResult showVerify(String email);

    ProcessResult saveUser(SysUser vo);

    ProcessResult saveOrUpdate(SysUser sysUser);

    ProcessResult batchDelete(Integer[] list);

    SysUser findByUserName(String username);

    ProcessResult saveAvatar(SysUser sysUser);

    List<SysUser> selectUserListByRoleId(Integer roleId);
}