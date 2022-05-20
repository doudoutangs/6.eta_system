package com.eta.modules.bs.service.impl;

import com.eta.core.entity.ProcessResult;
import com.eta.modules.bs.mapper.RecruitMapper;
import com.eta.modules.bs.model.Recruit;
import com.eta.modules.bs.service.RecruitService;
import com.eta.modules.constant.CommonConstant;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
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
public class RecruitServiceImpl implements RecruitService {

    @Autowired
    RecruitMapper recruitMapper;

    @Override
    public List<Recruit> getAllWithPage(Recruit param) {
        PageHelper.startPage(param.getPage(), param.getLimit());
        Example example = new Example(Recruit.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(param.getTitle())) {
            criteria.andLike("title", "%" + param.getTitle() + "%");
        }
        if (StringUtils.isNotBlank(param.getCompany())) {
            criteria.andLike("company", "%" + param.getCompany() + "%");
        }
        example.orderBy("id").desc();
        return recruitMapper.selectByExample(example);
    }

    @Override
    public List<Recruit> getAll() {
        return recruitMapper.selectAll();
    }

    @Override
    public ProcessResult saveOrUpdate(Recruit param) {
        Integer result = 0;

        if (param.getId() != null) {//update
            result = recruitMapper.updateByPrimaryKeySelective(param);
        } else {//insert
            param.setCreateTime(new Date());
            result = recruitMapper.insert(param);
        }
        if (result == CommonConstant.OPERATE_SUCCESS) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }

    @Override
    public Recruit getById(Integer id) {
        return recruitMapper.selectByPrimaryKey(id);
    }

    @Override
    public ProcessResult deleteById(Integer id) {
        Integer result = 0;
        result = recruitMapper.deleteByPrimaryKey(id);
        if (result == CommonConstant.OPERATE_SUCCESS) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }

    @Override
    public ProcessResult batchDelete(Integer[] list) {
        Integer result = recruitMapper.batchDelete(Arrays.asList(list));
        if (result == list.length) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }
}
