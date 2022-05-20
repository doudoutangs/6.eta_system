package com.eta.modules.bs.service.impl;

import com.eta.core.entity.ProcessResult;
import com.eta.core.util.MD5Utils;
import com.eta.modules.bs.mapper.SchoolClassMapper;
import com.eta.modules.bs.mapper.StudentMapper;
import com.eta.modules.bs.mapper.TestAnswerMapper;
import com.eta.modules.bs.mapper.WorkRegisterMapper;
import com.eta.modules.bs.model.*;
import com.eta.modules.bs.service.StudentService;
import com.eta.modules.constant.CommonConstant;
import com.eta.modules.sys.mapper.SysUserMapper;
import com.eta.modules.sys.mapper.SysUserRoleMapper;
import com.eta.modules.sys.model.SysRole;
import com.eta.modules.sys.model.SysUser;
import com.eta.modules.sys.model.SysUserRole;
import com.eta.modules.sys.service.UserService;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * Created by sugar on 2021/11/25.
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    SchoolClassMapper schoolClassMapper;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    UserService userService;
    @Autowired
    WorkRegisterMapper workRegisterMapper;

    @Autowired
    TestAnswerMapper testAnswerMapper;
    @Override
    public List<StudentVO> getAllWithPage(StudentVO param) {
        PageHelper.startPage(param.getPage(), param.getLimit());
        List<StudentVO> students = studentMapper.selectByParam(param);
        return students;
    }

    @Override
    public List<Student> getAll() {
        return studentMapper.selectAll();
    }

    @Override
    public List<Student> getChildList(String id) {
        return studentMapper.getChildList(id);
    }

    @Override
    public ProcessResult saveOrUpdate(Student param) {
        Integer result = 0;
        if (param.getId() != null) {//update
            result = studentMapper.updateByPrimaryKeySelective(param);
        } else {
            //insert

            //1.先创建登录账号
            String idNo = param.getIdNo();
            SysUser u = new SysUser();
            u.setUsername(idNo);
            SysUser user = sysUserMapper.selectOne(u);
            if (user != null) {
                return new ProcessResult(ProcessResult.ERROR, "该身份证号已被注册");
            }
            String birth = getBirthByIdNo(idNo);
            SysUser loginUser = createLoginUser(param);
            String no = "2" + StringUtils.leftPad(String.valueOf(loginUser.getId()), 6, "0");//学号
            param.setId(loginUser.getId());
            param.setNo(no);
            param.setBirth(birth);
            param.setCreateTime(new Date());
            param.setState("0");
            result = studentMapper.insert(param);
        }
        if (result == CommonConstant.OPERATE_SUCCESS) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }

    /**
     * 创建登录账号并绑定角色
     *
     * @param param
     */
    private SysUser createLoginUser(Student param) {
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
        sysUser.setDeptId(1002014);
        sysUser.setBirth(birth);
        sysUser.setEmail(param.getEmail());
        sysUser.setPhone(param.getPhone());
        sysUser.setCity(param.getCity());
        sysUser.setRoles(roles);
        ProcessResult save = userService.save(sysUser);
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(sysUser.getId());
        sysUserRole.setRoleId(2);//学生角色
        sysUserRoleMapper.insert(sysUserRole);
        return sysUser;
    }

    private String getBirthByIdNo(String idNo) {
        String year = idNo.substring(6, 10);
        String month = idNo.substring(10, 12);
        String day = idNo.substring(12, 14);
        return year + "-" + month + "-" + day;
    }

    @Override
    public Student getById(Integer id) {
        return studentMapper.selectByPrimaryKey(id);
    }

    @Override
    public ProcessResult deleteById(Integer id) {
        WorkRegister workRegister = workRegisterMapper.getByStudentId(id);
        if (workRegister != null) {
            return new ProcessResult(ProcessResult.ERROR, "请先删除该学生填报的就业登记表");
        }
        TestAnswer answer = testAnswerMapper.getByUserId(id);
        if (answer != null) {
            return new ProcessResult(ProcessResult.ERROR, "请先删除该学生填报的问卷调查表");
        }
        int result = studentMapper.deleteByPrimaryKey(id);
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
            Student param = new Student();
            param.setId(id);
            WorkRegister workRegister = workRegisterMapper.getByStudentId(id);
            if (workRegister != null) {
                return new ProcessResult(ProcessResult.ERROR, "请先删除该学生填报的就业登记表");
            }
            TestAnswer answer = testAnswerMapper.getByUserId(id);
            if (answer != null) {
                return new ProcessResult(ProcessResult.ERROR, "请先删除该学生填报的问卷调查表");
            }
        }
        result = studentMapper.batchDelete(Arrays.asList(list));

        if (result == list.length) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }
}
