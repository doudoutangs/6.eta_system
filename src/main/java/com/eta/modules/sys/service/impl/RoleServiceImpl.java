
package com.eta.modules.sys.service.impl;

import com.eta.core.entity.ProcessResult;
import com.eta.modules.constant.CommonConstant;
import com.github.pagehelper.PageHelper;
import com.eta.modules.sys.mapper.SysRoleMapper;
import com.eta.modules.sys.mapper.SysUserRoleMapper;
import com.eta.modules.sys.model.SysRole;
import com.eta.modules.sys.model.SysUserRole;
import com.eta.modules.sys.service.RoleService;
import com.eta.modules.sys.vo.SysRoleSelectVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<Integer> findByUserId(Integer userId) {
        List<SysRole> sysRoles = sysRoleMapper.findByUserId(userId);
        if (sysRoles != null && !sysRoles.isEmpty()) {
            List<Integer> roleIds = sysRoles.stream().map(sysRole -> sysRole.getId()).collect(Collectors.toList());
            return roleIds;
        }
        return null;
    }

    @Override
    public List<SysRole> getAll(SysRole sysRole, String keyword) {
        PageHelper.startPage(sysRole.getPage(), sysRole.getLimit());
        Example example = new Example(SysRole.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(keyword)) {
            keyword = "%" + keyword + "%";
            criteria.andLike("roleName", keyword);
            criteria.orLike("remark", keyword);
        }
        example.orderBy("roleType");
        return sysRoleMapper.selectByExample(example);
    }

    @Override
    public ProcessResult batchDelete(Integer[] list) {
        Integer result = sysRoleMapper.batchDelete(Arrays.asList(list));
        if (result == list.length) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }

    @Override
    public ProcessResult saveOrUpdate(SysRole sysRole) {
        Integer result = 0;

        if (sysRole.getId() != null) {//update
            result = sysRoleMapper.updateByPrimaryKeySelective(sysRole);
        } else {//insert
            sysRole.setCreateTime(new Date());
            sysRole.setRoleType(Byte.valueOf("1"));
            result = sysRoleMapper.insert(sysRole);
        }
        if (result == CommonConstant.OPERATE_SUCCESS) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }

    @Override
    public SysRole getById(Integer id) {
        return sysRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public ProcessResult deleteById(Integer id) {
        Integer result = 0;

        Example example = new Example(SysUserRole.class);
        example.createCriteria().andEqualTo("roleId", id);
        List<SysUserRole> list = sysUserRoleMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            throw new RuntimeException("当前角色存在关联用户,无法删除");
        }
        result = sysRoleMapper.deleteByPrimaryKey(id);
        if (result == CommonConstant.OPERATE_SUCCESS) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }

    @Override
    public List<SysRoleSelectVo> getAllRoles() {
        Example example = new Example(SysRole.class);
        example.createCriteria().andEqualTo("status", 0);
        List<SysRole> sysRoles = sysRoleMapper.selectByExample(example);
        return convertToRoleSelectVo(sysRoles);
    }

    private List<SysRoleSelectVo> convertToRoleSelectVo(List<SysRole> sysRoles) {
        List<SysRoleSelectVo> sysRoleSelectVos = new ArrayList<>();
        sysRoles.parallelStream().forEach(sysRole -> {
            SysRoleSelectVo srsv = new SysRoleSelectVo();
            srsv.setName(sysRole.getRoleName());
            srsv.setValue(sysRole.getId());
            sysRoleSelectVos.add(srsv);
        });
        return sysRoleSelectVos;
    }
}
