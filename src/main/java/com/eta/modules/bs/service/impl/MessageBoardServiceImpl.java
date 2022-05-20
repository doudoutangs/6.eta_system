package com.eta.modules.bs.service.impl;

import com.eta.core.entity.ProcessResult;
import com.eta.modules.bs.mapper.MessageBoardMapper;
import com.eta.modules.bs.model.MessageBoard;
import com.eta.modules.bs.service.MessageBoardService;
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
public class MessageBoardServiceImpl implements MessageBoardService {

    @Autowired
    MessageBoardMapper messageBoardMapper;

    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public List<MessageBoard> getAllWithPage(MessageBoard param) {
        PageHelper.startPage(param.getPage(), param.getLimit());
        Example example = new Example(MessageBoard.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(param.getTitle())) {
            criteria.andLike("title", "%" + param.getTitle() + "%");
        }
        if (StringUtils.isNotBlank(param.getReleaseDate())) {
            criteria.andLike("releaseDate", param.getReleaseDate() + "%");
        }
        example.orderBy("id").desc();
        List<MessageBoard> list = messageBoardMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            for (MessageBoard s : list) {
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
    public List<MessageBoard> getAll() {
        return messageBoardMapper.selectAll();
    }

    @Override
    public ProcessResult saveOrUpdate(MessageBoard param) {
        Integer result = 0;

        if (param.getId() != null) {//update
            result = messageBoardMapper.updateByPrimaryKeySelective(param);
        } else {//insert
            param.setCreateTime(new Date());
            result = messageBoardMapper.insert(param);
        }
        if (result == CommonConstant.OPERATE_SUCCESS) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }

    @Override
    public MessageBoard getById(Integer id) {
        return messageBoardMapper.selectByPrimaryKey(id);
    }

    @Override
    public ProcessResult deleteById(Integer id) {
        Integer result = 0;

        result = messageBoardMapper.deleteByPrimaryKey(id);
        if (result == CommonConstant.OPERATE_SUCCESS) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }

    @Override
    public ProcessResult batchDelete(Integer[] list) {
        Integer result = messageBoardMapper.batchDelete(Arrays.asList(list));
        if (result == list.length) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }
}
