package com.eta.modules.sys.service.impl;

import com.eta.core.entity.ProcessResult;
import com.eta.modules.constant.CommonConstant;
import com.github.pagehelper.PageHelper;
import com.eta.modules.sys.mapper.SysPostMapper;
import com.eta.modules.sys.mapper.SysUserMapper;
import com.eta.modules.sys.model.SysPost;
import com.eta.modules.sys.model.SysUser;
import com.eta.modules.sys.service.PostService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;

/**
 *
 **/
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    SysPostMapper sysPostMapper;

    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public List<SysPost> getAllWithPage(SysPost sysPost) {
        PageHelper.startPage(sysPost.getPage(), sysPost.getLimit());
        Example example = new Example(SysPost.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(sysPost.getPostCode())) {
            criteria.andLike("postCode", "%" + sysPost.getPostCode() + "%");
        }
        if (StringUtils.isNotBlank(sysPost.getPostName())) {
            criteria.andLike("postName", "%" + sysPost.getPostName() + "%");
        }
        if (StringUtils.isNotBlank(sysPost.getStatus())) {
            criteria.andEqualTo("status", sysPost.getStatus());
        }
        example.orderBy("createTime").desc();
        return sysPostMapper.selectByExample(example);
    }

    @Override
    public List<SysPost> getAllPost() {
        Example example = new Example(SysPost.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", 0);
        return sysPostMapper.selectByExample(example);
    }

    @Override
    public ProcessResult saveOrUpdate(SysPost sysPost) {
        Integer result = 0;

        if (sysPost.getPostId() != null) {//update
            result = sysPostMapper.updateByPrimaryKeySelective(sysPost);
        } else {//insert
            result = sysPostMapper.insert(sysPost);
        }
        if (result == CommonConstant.OPERATE_SUCCESS) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }

    @Override
    public SysPost getById(Integer id) {
        return sysPostMapper.selectByPrimaryKey(id);
    }

    @Override
    public ProcessResult deleteById(Integer id) {
        Integer result = 0;

        Example example = new Example(SysUser.class);
        example.createCriteria().andEqualTo("postId", id);
        List<SysUser> list = sysUserMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            throw new RuntimeException("当前岗位存在关联用户,无法删除");
        }
        result = sysPostMapper.deleteByPrimaryKey(id);
        if (result == CommonConstant.OPERATE_SUCCESS) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }

    @Override
    public ProcessResult batchDelete(Integer[] list) {
        Integer result = sysPostMapper.batchDelete(Arrays.asList(list));

        if (result == list.length) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }
}
