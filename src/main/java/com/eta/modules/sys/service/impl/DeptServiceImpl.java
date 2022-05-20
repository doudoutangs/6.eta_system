
package com.eta.modules.sys.service.impl;

import com.eta.core.entity.ProcessResult;
import com.eta.modules.constant.CommonConstant;
import com.eta.modules.sys.mapper.SysDeptMapper;
import com.eta.modules.sys.mapper.SysDeptUserMapper;
import com.eta.modules.sys.mapper.SysUserMapper;
import com.eta.modules.sys.model.SysDept;
import com.eta.modules.sys.service.DeptService;
import com.eta.modules.sys.vo.SysDeptSelectVo;
import com.eta.modules.sys.vo.SysDeptVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qjp
 * @since 2016-01-31 21:42
 */
@Service("deptService")
@Transactional
public class DeptServiceImpl implements DeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private SysDeptUserMapper sysDeptUserMapper;
    @Autowired
    private SysUserMapper sysUserMapper;


    @Override
    public List<SysDept> getAll() {
        return sysDeptMapper.selectAll();
    }

    @Override
    public List<SysDeptVo> treeList() {
        List<SysDept> toplist = sysDeptMapper.ListTopDept();
        return convertVo(toplist);
    }

    @Override
    public List<SysDept> getChildDeptList(Integer id) {
        return sysDeptMapper.getChildDeptList(id);
    }

    @Override
    public List<SysDeptSelectVo> treeSelectList() {
        List<SysDept> toplist = sysDeptMapper.ListTopDept();
        return convertSelectVo(toplist);
    }

    @Override
    public SysDept getDeptById(Integer id) {
        return sysDeptMapper.selectByPrimaryKey(id);
    }

    @Override
    public ProcessResult saveOrUpdate(SysDept sysDept) throws Exception {
        Integer result = 0;
        if (sysDept.getId() != null) {
            SysDept newParentDept = sysDeptMapper.selectDeptById(sysDept.getParentId());
            SysDept oldDept = sysDeptMapper.selectDeptById(sysDept.getId());
            if (newParentDept != null && oldDept != null) {
                String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getId();
                String oldAncestors = oldDept.getAncestors();
                sysDept.setAncestors(newAncestors);
                updateDeptChildren(sysDept.getId(), newAncestors, oldAncestors);
            }
            result = sysDeptMapper.updateByPrimaryKey(sysDept);
        } else {
            SysDept query = new SysDept();
            query.setDeptName(sysDept.getDeptName());
            query.setParentId(sysDept.getParentId());
            List<SysDept> sysDepts = sysDeptMapper.select(query);
            if (sysDepts.size() > 0) {
                throw new Exception("部门【" + sysDept.getDeptName() + "】已存在");
            }
            SysDept dept = sysDeptMapper.selectDeptById(sysDept.getParentId());
            sysDept.setAncestors(dept.getAncestors() + "," + sysDept.getParentId());
            result = sysDeptMapper.insert(sysDept);
        }
        if (result == CommonConstant.OPERATE_SUCCESS) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }

    public void updateDeptChildren(Integer deptId, String newAncestors, String oldAncestors) {
        List<SysDept> children = sysDeptMapper.selectChildrenDeptById(deptId);
        for (SysDept child : children) {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
        }
        if (children.size() > 0) {
            sysDeptMapper.updateDeptChildren(children);
        }
    }



    private Map<String, Object> convertTree(List<SysDept> toplist) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", convertVo(toplist));
        return map;
    }

    private List<SysDeptVo> convertVo(List<SysDept> toplist) {
        List<SysDeptVo> result = new ArrayList<>();
        if (toplist.size() > 0) {
            for (SysDept sysDept : toplist) {
                SysDeptVo vo = new SysDeptVo();
                vo.setId(sysDept.getId());
                vo.setLabel(sysDept.getDeptName());
                vo.setSpread(true);
                List<SysDept> list = sysDeptMapper.getChildDeptList(sysDept.getId());
                if (list.size() > 0) {
                    vo.setChildren(convertVo(list));
                }
                result.add(vo);
            }
        }
        return result;
    }

    private List<SysDeptSelectVo> convertSelectVo(List<SysDept> toplist) {
        List<SysDeptSelectVo> result = new ArrayList<>();
        if (toplist.size() > 0) {
            for (SysDept sysDept : toplist) {
                SysDeptSelectVo vo = new SysDeptSelectVo();
                vo.setId(sysDept.getId());
                vo.setName(sysDept.getDeptName());
                vo.setOpen(true);
                List<SysDept> list = sysDeptMapper.getChildDeptList(sysDept.getId());
                if (list.size() > 0) {
                    vo.setChildren(convertSelectVo(list));
                }
                result.add(vo);
            }
        }
        return result;
    }

    @Override
    public ProcessResult deleteById(Integer id) {
        List<SysDept> childDeptList = sysDeptMapper.getChildDeptList(id);
        if(childDeptList.size()>0){
            return new ProcessResult(ProcessResult.ERROR, "该部门下有子部门，请先删除其子部门");
        }
        Integer result = sysDeptMapper.deleteByPrimaryKey(id);
        if (result == CommonConstant.OPERATE_SUCCESS) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }
}
