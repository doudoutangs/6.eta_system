package com.eta.modules.bs.service.impl;

import com.eta.core.entity.ProcessResult;
import com.eta.core.util.IDUtil;
import com.eta.modules.bs.mapper.MajorMapper;
import com.eta.modules.bs.mapper.UniversitiesMapper;
import com.eta.modules.bs.model.Major;
import com.eta.modules.bs.model.Universities;
import com.eta.modules.bs.service.MajorService;
import com.eta.modules.constant.CommonConstant;
import com.eta.modules.constant.DictConstant;
import com.eta.modules.sys.mapper.SysDictMapper;
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
public class MajorServiceImpl implements MajorService {

    @Autowired
    MajorMapper majorMapper;
    @Autowired
    SysDictMapper dictMapper;
    @Autowired
    UniversitiesMapper universitiesMapper;

    @Override
    public List<Major> getAllWithPage(Major param) {
        PageHelper.startPage(param.getPage(), param.getLimit());
        Example example = new Example(Major.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(param.getName())) {
            criteria.andLike("name", "%" + param.getName() + "%");
        }
        if (StringUtils.isNotBlank(param.getNo())) {
            criteria.andLike("no", "%" + param.getNo() + "%");
        }
        if (null != param.getUniversitiesId()) {
            criteria.andEqualTo("universitiesId", param.getUniversitiesId());
        }
        example.orderBy("id").desc();
        List<Major> majors = majorMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(majors)) {
            for (Major major : majors) {
                if (null != major.getUniversitiesId()) {
                    Universities universities = universitiesMapper.selectByPrimaryKey(major.getUniversitiesId());
                    if (null != universities) {
                        major.setUniversitiesName(universities.getName());
                    }
                    if (StringUtils.isNotBlank(major.getCategory())) {
                        SysDict dict = dictMapper.getDictByValueAndLevel(major.getCategory(), DictConstant.MAJOR_TYPE);
                        major.setCategoryName(dict.getDictName());
                    }
                }
            }
        }
        return majors;
    }

    @Override
    public List<Major> getAll() {
        return majorMapper.selectAll();
    }

    @Override
    public List<Major> getChildList(String id) {
        return majorMapper.getChildList(id);
    }

    @Override
    public ProcessResult saveOrUpdate(Major param) {
        Integer result = 0;
        if (StringUtils.isNotBlank(param.getId())) {//update
            result = majorMapper.updateByPrimaryKeySelective(param);
        } else {//insert
            param.setCreateTime(new Date());
            param.setId(IDUtil.randomUnion(param.getUniversitiesId()));
            result = majorMapper.insert(param);
        }
        if (result == CommonConstant.OPERATE_SUCCESS) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }

    @Override
    public Major getById(String id) {
        return majorMapper.selectByPrimaryKey(id);
    }

    @Override
    public ProcessResult deleteById(String id) {
        int result = majorMapper.deleteByPrimaryKey(id);
        if (result == CommonConstant.OPERATE_SUCCESS) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }

    @Override
    public ProcessResult batchDelete(String[] list) {
        Integer result = majorMapper.batchDelete(Arrays.asList(list));

        if (result == list.length) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }
}
