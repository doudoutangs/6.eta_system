package com.eta.modules.bs.service.impl;

import cn.hutool.core.date.DateUtil;
import com.eta.core.entity.ProcessResult;
import com.eta.modules.bs.mapper.AnalysisReportMapper;
import com.eta.modules.bs.mapper.MajorMapper;
import com.eta.modules.bs.mapper.SchoolClassMapper;
import com.eta.modules.bs.model.AnalysisReport;
import com.eta.modules.bs.model.OrgTree;
import com.eta.modules.bs.service.AnalysisReportService;
import com.eta.modules.constant.CommonConstant;
import com.eta.modules.sys.mapper.SysUserMapper;
import com.eta.modules.sys.model.SysUser;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by sugar on 2021/11/25.
 */
@Service
public class AnalysisReportServiceImpl implements AnalysisReportService {

    @Autowired
    AnalysisReportMapper analysisReportMapper;
    @Autowired
    SysUserMapper userMapper;

    @Override
    public List<AnalysisReport> getAllWithPage(AnalysisReport param) {
        PageHelper.startPage(param.getPage(), param.getLimit());
        Example example = new Example(AnalysisReport.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(param.getName())) {
            criteria.andLike("name", "%" + param.getName() + "%");
        }
        example.orderBy("id").desc();
        List<AnalysisReport> list = analysisReportMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            for (AnalysisReport u : list) {
                if (u.getUserId()!=null) {
                    SysUser user = userMapper.selectByPK(u.getUserId());
                    u.setUserName(user.getRealName());
                }
            }
        }
        return list;
    }

    @Override
    public List<AnalysisReport> getAll() {
        return analysisReportMapper.selectAll();
    }

    @Override
    public ProcessResult saveOrUpdate(AnalysisReport param) {
        Integer result = 0;
        if (param.getId() != null) {//update
            param.setUpdateTime(DateUtil.now());
            result = analysisReportMapper.updateByPrimaryKeySelective(param);
        } else {//insert
            param.setCreateTime(DateUtil.now());
            result = analysisReportMapper.insert(param);
        }
        if (result == CommonConstant.OPERATE_SUCCESS) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }

    }

    @Override
    public AnalysisReport getById(Integer id) {
        return analysisReportMapper.selectByPrimaryKey(id);
    }

    @Override
    public ProcessResult deleteById(Integer id) {
        Integer result = 0;

        result = analysisReportMapper.deleteByPrimaryKey(id);
        if (result == CommonConstant.OPERATE_SUCCESS) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }

    @Override
    public ProcessResult batchDelete(Integer[] list) {
        Integer result = analysisReportMapper.batchDelete(Arrays.asList(list));
        if (result == list.length) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }
}
