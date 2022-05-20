package com.eta.modules.sys.mapper;

import com.eta.core.mapper.MyMapper;
import com.eta.modules.sys.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysUserMapper extends MyMapper<SysUser> {
    List<SysUser> getIsNotOwner(@Param("roleId") Integer roleId, @Param("keyword") String keyword);

    List<SysUser> getIsOwner(@Param("roleId") Integer roleId, @Param("keyword") String keyword);

    SysUser getMasterUserByDept(Integer deptId);

    List<SysUser> isUnAllotUserList(Integer deptId);

    List<SysUser> isAllotUserList(Integer deptId);

    List<SysUser> selectUserByDeptId(Map<String,Object> map);

    SysUser selectByPK(Integer id);

    List<SysUser> selectUserByRoleId(Integer roleId);
    Integer batchDelete(List<Integer> list);

}