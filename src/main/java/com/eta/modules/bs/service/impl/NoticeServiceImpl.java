package com.eta.modules.bs.service.impl;

import com.eta.core.entity.ProcessResult;
import com.eta.modules.bs.mapper.NoticeMapper;
import com.eta.modules.bs.model.Notice;
import com.eta.modules.bs.service.NoticeService;
import com.eta.modules.constant.CommonConstant;
import com.eta.modules.sys.mapper.SysUserMapper;
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
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    NoticeMapper noticeMapper;
    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public List<Notice> getAllWithPage(Notice param) {
        PageHelper.startPage(param.getPage(), param.getLimit());
        Example example = new Example(Notice.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(param.getTitle())) {
            criteria.andLike("title", "%" + param.getTitle() + "%");
        }
        if (null != param.getState()) {
            criteria.andEqualTo("state", param.getState());
        }
        example.orderBy("id").desc();
        List<Notice> list = noticeMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            for (Notice s : list) {
                SysUser sysUser = sysUserMapper.selectByPrimaryKey(s.getUserId());
                if (null != sysUser) {
                    s.setUserName(sysUser.getRealName());
                } else {
                    s.setUserName(s.getUserId() + "");
                }
            }
        }
        return list;
    }

    @Override
    public List<Notice> getAll() {
        return noticeMapper.selectAll();
    }

    @Override
    public ProcessResult saveOrUpdate(Notice param) {
        Integer result = 0;

        if (param.getId() != null) {//update
            result = noticeMapper.updateByPrimaryKeySelective(param);
        } else {//insert
            param.setCreateTime(new Date());
            result = noticeMapper.insert(param);
        }
        if (result == CommonConstant.OPERATE_SUCCESS) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }

    @Override
    public Notice getById(Integer id) {
        return noticeMapper.selectByPrimaryKey(id);
    }

    @Override
    public ProcessResult deleteById(Integer id) {
        Integer result = 0;

        result = noticeMapper.deleteByPrimaryKey(id);
        if (result == CommonConstant.OPERATE_SUCCESS) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }

    @Override
    public ProcessResult batchDelete(Integer[] list) {

        Integer result = noticeMapper.batchDelete(Arrays.asList(list));
        if (result == list.length) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }
}
