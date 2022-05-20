package com.eta.modules.bs.service.impl;

import com.eta.core.entity.ProcessResult;
import com.eta.core.util.MD5Utils;
import com.eta.modules.bs.mapper.SchoolClassMapper;
import com.eta.modules.bs.mapper.TeacherMapper;
import com.eta.modules.bs.model.SchoolClass;
import com.eta.modules.bs.model.Teacher;
import com.eta.modules.bs.service.TeacherService;
import com.eta.modules.constant.CommonConstant;
import com.eta.modules.sys.mapper.SysUserRoleMapper;
import com.eta.modules.sys.model.SysRole;
import com.eta.modules.sys.model.SysUser;
import com.eta.modules.sys.model.SysUserRole;
import com.eta.modules.sys.service.UserService;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * Created by sugar on 2021/11/25.
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    UserService userService;
    @Autowired
    SchoolClassMapper schoolClassMapper;

    @Override
    public List<Teacher> getAllWithPage(Teacher param) {
        PageHelper.startPage(param.getPage(), param.getLimit());
        Example example = new Example(Teacher.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(param.getName())) {
            criteria.andLike("name", "%" + param.getName() + "%");
        }
        if (StringUtils.isNotBlank(param.getNo())) {
            criteria.andLike("no", "%" + param.getNo() + "%");
        }
        example.orderBy("id").desc();
        return teacherMapper.selectByExample(example);
    }

    @Override
    public List<Teacher> getAll() {
        return teacherMapper.selectAll();
    }

    @Override
    public ProcessResult saveOrUpdate(Teacher param) {
        int result= 0;
        if (param.getId() != null) {//update
            result =   teacherMapper.updateByPrimaryKeySelective(param);
        } else {//insert
            //1.先创建登录账号
            String idNo = param.getIdNo();
            String birth = getBirthByIdNo(idNo);
            SysUser loginUser = createLoginUser(param);

            String no = "2" + StringUtils.leftPad(String.valueOf(loginUser.getId()), 6, "0");//学号
            param.setId(loginUser.getId());
            param.setNo(no);
            param.setBirth(birth);
            param.setCreateTime(new Date());
            result = teacherMapper.insert(param);
        }
        if (result == CommonConstant.OPERATE_SUCCESS) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }

    private String getBirthByIdNo(String idNo) {
        String year = idNo.substring(6, 10);
        String month = idNo.substring(10, 12);
        String day = idNo.substring(12, 14);
        return year + "-" + month + "-" + day;
    }

    /**
     * 创建登录账号并绑定角色
     *
     * @param param
     */
    private SysUser createLoginUser(Teacher param) {
        String idNo = param.getIdNo();
        String birth = getBirthByIdNo(idNo);
        //角色
        Set<SysRole> roles = new HashSet<SysRole>();
        SysRole sysRole = new SysRole();
        sysRole.setId(2);
        roles.add(sysRole);

        SysUser sysUser = new SysUser();
        sysUser.setIsSysUser("1");
        sysUser.setUsername(param.getIdNo());
        sysUser.setPassword(MD5Utils.md5(idNo.substring(12, idNo.length())));
        sysUser.setStatus(0);
        sysUser.setCreateTime(new Date());
        sysUser.setRealName(param.getName());
        sysUser.setSex(Integer.valueOf(param.getGender()));
        sysUser.setDeptId(1001);
        sysUser.setBirth(birth);
        sysUser.setEmail(param.getEmail());
        sysUser.setPhone(param.getPhone());
        sysUser.setCity(param.getCity());
        sysUser.setRoles(roles);
        //1.增加登录账号
        ProcessResult save = userService.save(sysUser);
        //2.绑定角色
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(sysUser.getId());
        sysUserRole.setRoleId(3);//老师角色
        sysUserRoleMapper.insert(sysUserRole);
        return sysUser;
    }

    @Override
    public Teacher getById(Integer id) {
        return teacherMapper.selectByPrimaryKey(id);
    }

    @Override
    public ProcessResult deleteById(Integer id) {
        Example example = new Example(SchoolClass.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("teacherId", id);
        List<SchoolClass> list = schoolClassMapper.selectByExample(example);
        if (list.size() > 0) {
            return new ProcessResult(ProcessResult.ERROR, "该老师在班级，" + list.get(0).getName() + "中是班主任，请先更换该班的班主为其他老师再删除");
        }
        int result = teacherMapper.deleteByPrimaryKey(id);
        if (result == CommonConstant.OPERATE_SUCCESS) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }

    @Override
    public ProcessResult batchDelete(Integer[] list) {
        Integer result = 0;
        for (Integer id : list) {
            Teacher param = new Teacher();
            param.setId(id);
            Example example = new Example(SchoolClass.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("teacherId", id);
            List<SchoolClass> schools = schoolClassMapper.selectByExample(example);
            if (schools.size() > 0) {
                return new ProcessResult(ProcessResult.ERROR, "该老师在班级，" + schools.get(0).getName() + "中是班主任，请先更换该班的班主为其他老师再删除");
            }
        }
        result = teacherMapper.batchDelete(Arrays.asList(list));
        if (result == list.length) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }
}
