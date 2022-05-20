package com.eta.modules.sys.mapper;


import com.eta.core.mapper.MyMapper;
import com.eta.modules.sys.model.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDeptMapper extends MyMapper<SysDept> {
    List<SysDept> ListTopDept();

    List<SysDept> getChildDeptList(Integer id);

    SysDept selectDeptById(Integer parentId);

    /**
     * 修改子元素关系
     *
     * @param depts 子元素
     * @return 结果
     */
    int updateDeptChildren(@Param("depts") List<SysDept> depts);

    /**
     * 根据ID查询所有子部门
     *
     * @param id 部门ID
     * @return 部门列表
     */
    List<SysDept> selectChildrenDeptById(Integer id);

}