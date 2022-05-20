package com.eta.modules.bs.service.impl;

import com.eta.core.entity.ProcessResult;
import com.eta.core.util.IDUtil;
import com.eta.modules.bs.mapper.MajorMapper;
import com.eta.modules.bs.mapper.SchoolClassMapper;
import com.eta.modules.bs.mapper.UniversitiesMapper;
import com.eta.modules.bs.model.OrgTree;
import com.eta.modules.bs.model.Universities;
import com.eta.modules.bs.service.UniversitiesService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by sugar on 2021/11/25.
 */
@Service
public class UniversitiesServiceImpl implements UniversitiesService {

    @Autowired
    UniversitiesMapper universitiesMapper;
    @Autowired
    SysDictMapper dictMapper;
    @Autowired
    MajorMapper majorMapper;
    @Autowired
    SchoolClassMapper schoolClassMapper;

    @Override
    public List<Universities> getAllWithPage(Universities param) {
        PageHelper.startPage(param.getPage(), param.getLimit());
        Example example = new Example(Universities.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(param.getName())) {
            criteria.andLike("name", "%" + param.getName() + "%");
        }
        if (StringUtils.isNotBlank(param.getNo())) {
            criteria.andLike("no", "%" + param.getNo() + "%");
        }
        example.orderBy("id").desc();
        List<Universities> list = universitiesMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            for (Universities u : list) {
                if (StringUtils.isNotBlank(u.getCity())) {
                    SysDict city = dictMapper.getDictByValueAndLevel(u.getCity(), DictConstant.CITY);
                    u.setCityName(city.getDictName());
                }
            }
        }
        return list;
    }

    @Override
    public List<Universities> getAll() {
        return universitiesMapper.selectAll();
    }

    @Override
    public ProcessResult saveOrUpdate(Universities param) {
        Integer result = 0;
        if (StringUtils.isNotBlank(param.getId())) {//update
            result = universitiesMapper.updateByPrimaryKeySelective(param);
        } else {//insert
            Universities u = new Universities();
            u.setNo(param.getNo());
            Universities universities = universitiesMapper.selectOne(u);
            if (null != universities) {
                return new ProcessResult(ProcessResult.ERROR, "院系代码:" + param.getNo() + "已存在");
            }
            param.setId(IDUtil.random4());
            param.setCreateTime(new Date());
            result = universitiesMapper.insert(param);
        }
        if (result == CommonConstant.OPERATE_SUCCESS) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }

    }

    @Override
    public Universities getById(String id) {
        return universitiesMapper.selectByPrimaryKey(id);
    }

    @Override
    public ProcessResult deleteById(String id) {
        Integer result = 0;

        result = universitiesMapper.deleteByPrimaryKey(id);
        if (result == CommonConstant.OPERATE_SUCCESS) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }

    @Override
    public ProcessResult batchDelete(String[] list) {
        Integer result = universitiesMapper.batchDelete(Arrays.asList(list));
        if (result == list.length) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }

    @Override
    public List<OrgTree> getAllOrg() {
        List<OrgTree> list = universitiesMapper.getAllOrg();
        List<OrgTree> orgTrees = list2Tree("-1", list);
        return orgTrees;
    }

    /**
     * 列表转成树
     *
     * @param parentId
     * @param list
     * @return
     */
    private List<OrgTree> list2Tree(String parentId, List<OrgTree> list) {
        List<OrgTree> result = new ArrayList<>();
        for (OrgTree node : list) {
            if (parentId.equals(node.getParentId())) {
                result.add(node);
                List<OrgTree> children = list2Tree(node.getId(), list);
                if (children.size() > 0) {
                    node.setChildren(children);
                }
            }
        }
        return result;
    }
}
