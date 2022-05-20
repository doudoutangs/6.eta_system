package com.eta.modules.bs.service.impl;

import com.eta.core.entity.ProcessResult;
import com.eta.modules.bs.mapper.StudentMapper;
import com.eta.modules.bs.mapper.WorkRegisterMapper;
import com.eta.modules.bs.model.Student;
import com.eta.modules.bs.model.Universities;
import com.eta.modules.bs.model.WorkRegister;
import com.eta.modules.bs.service.WorkRegisterService;
import com.eta.modules.constant.CommonConstant;
import com.eta.modules.constant.DictConstant;
import com.eta.modules.sys.mapper.SysDictMapper;
import com.eta.modules.sys.mapper.SysUserMapper;
import com.eta.modules.sys.model.SysDict;
import com.eta.modules.sys.model.SysUser;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by sugar on 2021/11/25.
 */
@Service
public class WorkRegisterServiceImpl implements WorkRegisterService {

    @Autowired
    StudentMapper studentMapper;
    @Autowired
    SysDictMapper sysDictMapper;
    @Autowired
    WorkRegisterMapper workRegisterMapper;

    @Override
    public List<WorkRegister> getAllWithPage(WorkRegister param) {
        PageHelper.startPage(param.getPage(), param.getLimit());
        Example example = new Example(WorkRegister.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(param.getCompany())) {
            criteria.andLike("company", "%" + param.getCompany() + "%");
        }
        if (null != param.getStudentId()) {
            criteria.andEqualTo("studentId", param.getStudentId());

        }
        if (StringUtils.isNotBlank(param.getWorkCity())) {
            criteria.andEqualTo("workCity", param.getWorkCity());
        }
        example.orderBy("createTime").desc();
        List<WorkRegister> list = workRegisterMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            for (WorkRegister u : list) {
                if (StringUtils.isNotBlank(u.getWorkCity())) {
                    SysDict city = sysDictMapper.getDictByValueAndLevel(u.getWorkCity(), DictConstant.CITY);
                    if (null != city) {
                        u.setWorkCityName(city.getDictName());
                    } else {
                        u.setWorkCityName(u.getWorkCity());
                    }
                }
                if (StringUtils.isNotBlank(u.getCompanyNature())) {
                    SysDict dict = sysDictMapper.getDictByValueAndLevel(u.getCompanyNature(), DictConstant.COMPANY_NATURE);
                    if (null != dict) {
                        u.setCompanyNatureName(dict.getDictName());
                    } else {
                        u.setCompanyNature(u.getCompanyNature());
                    }
                }
                if (null != u.getStudentId()) {
                    Student student = studentMapper.selectByPrimaryKey(u.getStudentId());
                    if (student != null) {
                        u.setStudentName(student.getName());
                    } else {
                        u.setStudentName(u.getStudentId() + "");
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<WorkRegister> getAll() {
        return workRegisterMapper.selectAll();
    }

    @Override
    public ProcessResult saveOrUpdate(WorkRegister param) {
        Integer result = 0;

        if (param.getId() != null) {//update
            result = workRegisterMapper.updateByPrimaryKeySelective(param);
        } else {//insert
            WorkRegister workRegister = workRegisterMapper.getByStudentId(param.getStudentId());
            if (workRegister != null) {
                return new ProcessResult(ProcessResult.ERROR, "该学生已填报了就业登记表");
            }
            param.setCreateTime(new Date());
            param.setRegisterYear(param.getRegisterDate().substring(0,4));
            result = workRegisterMapper.insert(param);
        }
        if (result == CommonConstant.OPERATE_SUCCESS) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }

    @Override
    public WorkRegister getById(Integer id) {
        return workRegisterMapper.selectByPrimaryKey(id);
    }

    @Override
    public ProcessResult deleteById(Integer id) {
        Integer result = 0;
        result = workRegisterMapper.deleteByPrimaryKey(id);
        if (result == CommonConstant.OPERATE_SUCCESS) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }

    @Override
    public ProcessResult batchDelete(Integer[] list) {
        Integer result = workRegisterMapper.batchDelete(Arrays.asList(list));

        if (result == list.length) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }
}
