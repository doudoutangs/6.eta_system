package com.eta.modules.bs.service.impl;

import com.eta.core.entity.ProcessResult;
import com.eta.modules.bs.mapper.RecruitMeetingMapper;
import com.eta.modules.bs.model.RecruitMeeting;
import com.eta.modules.bs.service.RecruitMeetingService;
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
public class RecruitMeetingServiceImpl implements RecruitMeetingService {

    @Autowired
    RecruitMeetingMapper recruitMeetingMapper;

    @Override
    public List<RecruitMeeting> getAllWithPage(RecruitMeeting param) {
        PageHelper.startPage(param.getPage(), param.getLimit());
        Example example = new Example(RecruitMeeting.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(param.getTitle())) {
            criteria.andLike("title", "%" + param.getTitle() + "%");
        }
        if (StringUtils.isNotBlank(param.getCompany())) {
            criteria.andLike("company", "%" + param.getCompany() + "%");
        }
        example.orderBy("id").desc();
        return recruitMeetingMapper.selectByExample(example);
    }

    @Override
    public List<RecruitMeeting> getAll() {
        return recruitMeetingMapper.selectAll();
    }

    @Override
    public ProcessResult saveOrUpdate(RecruitMeeting param) {
        Integer result = 0;
        if (param.getId() != null) {//update
            result = recruitMeetingMapper.updateByPrimaryKeySelective(param);
        } else {//insert
            param.setCreateTime(new Date());
            result = recruitMeetingMapper.insert(param);
        }
        if (result == CommonConstant.OPERATE_SUCCESS) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }

    @Override
    public RecruitMeeting getById(Integer id) {
        return recruitMeetingMapper.selectByPrimaryKey(id);
    }

    @Override
    public ProcessResult deleteById(Integer id) {
        Integer result = 0;

        result = recruitMeetingMapper.deleteByPrimaryKey(id);
        if (result == CommonConstant.OPERATE_SUCCESS) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }

    @Override
    public ProcessResult batchDelete(Integer[] list) {
        Integer result = recruitMeetingMapper.batchDelete(Arrays.asList(list));
        if (result == list.length) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }
}
