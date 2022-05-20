package com.eta.modules.bs.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.eta.core.entity.ProcessResult;
import com.eta.core.util.IDUtil;
import com.eta.modules.bs.mapper.MajorMapper;
import com.eta.modules.bs.mapper.SchoolClassMapper;
import com.eta.modules.bs.mapper.StudentMapper;
import com.eta.modules.bs.mapper.TeacherMapper;
import com.eta.modules.bs.model.Major;
import com.eta.modules.bs.model.SchoolClass;
import com.eta.modules.bs.model.Teacher;
import com.eta.modules.bs.model.Universities;
import com.eta.modules.bs.service.SchoolClassService;
import com.eta.modules.constant.CommonConstant;
import com.eta.modules.constant.DictConstant;
import com.eta.modules.sys.model.SysDict;
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
public class SchoolClassServiceImpl implements SchoolClassService {

    @Autowired
    SchoolClassMapper schoolClassMapper;

    @Autowired
    MajorMapper majorMapper;

    @Autowired
    TeacherMapper teacherMapper;

    @Override
    public List<SchoolClass> getAllWithPage(SchoolClass param) {
        PageHelper.startPage(param.getPage(), param.getLimit());
        Example example = new Example(SchoolClass.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(param.getName())) {
            criteria.andLike("name", "%" + param.getName() + "%");
        }
        if (StringUtils.isNotBlank(param.getNo())) {
            criteria.andLike("no", "%" + param.getNo() + "%");
        }
        if (StringUtils.isNotBlank(param.getTerm())) {
            criteria.andEqualTo("term", param.getTerm());
        }
        if (StringUtils.isNotBlank(param.getMajorId())) {
            criteria.andEqualTo("majorId", param.getMajorId());
        }
        example.orderBy("id").desc();
        List<SchoolClass> list = schoolClassMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            for (SchoolClass t : list) {
                if (null != t.getMajorId()) {
                    Major major = majorMapper.selectByPrimaryKey(t.getMajorId());
                    if (null != major) {
                        t.setMajorName(major.getName());
                    }
                }
                Integer teacherId = t.getTeacherId();
                Teacher teacher = teacherMapper.selectByPrimaryKey(teacherId);
                if (null != teacher) {
                    t.setTeacherName(teacher.getName());
                } else {
                    t.setTeacherName(teacherId + "");
                }
            }
        }

        return list;
    }

    @Override
    public List<SchoolClass> getAll() {
        return schoolClassMapper.selectAll();
    }

    @Override
    public List<SchoolClass> getChildList(String id) {
        return schoolClassMapper.getChildList(id);
    }

    @Override
    public ProcessResult saveOrUpdate(SchoolClass param) {
        Integer result = 0;

        String majorId = param.getMajorId();
        //根据专业学制计算毕业日期
        Major major = majorMapper.selectByPrimaryKey(majorId);
        Long year = major.getYear();
        String startYear = param.getStartDate().substring(0, 4);
        String endDate = Long.parseLong(startYear) + year + "-06-30";
        String no = StringUtils.leftPad(startYear+RandomUtil.randomInt(0,999), 7, "0");//学号
        param.setNo(no);
        param.setEndDate(endDate);
        param.setTerm(startYear);
        if (StringUtils.isNotBlank(param.getId())) {//update
            result = schoolClassMapper.updateByPrimaryKeySelective(param);
        } else {//insert
            param.setCreateTime(new Date());
            param.setId(IDUtil.randomUnion(majorId));
            result = schoolClassMapper.insert(param);
        }
        if (result == CommonConstant.OPERATE_SUCCESS) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }

    @Override
    public SchoolClass getById(String id) {
        return schoolClassMapper.selectByPrimaryKey(id);
    }

    @Override
    public ProcessResult deleteById(String id) {
        Integer result = 0;
        result = schoolClassMapper.deleteByPrimaryKey(id);
        if (result == CommonConstant.OPERATE_SUCCESS) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }

    @Override
    public ProcessResult batchDelete(String[] list) {
        Integer result = schoolClassMapper.batchDelete(Arrays.asList(list));
        if (result == list.length) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }
}
