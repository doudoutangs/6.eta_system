package com.eta.modules.sys.service.impl;

import com.eta.core.entity.ProcessResult;
import com.eta.modules.constant.CommonConstant;
import com.github.pagehelper.PageHelper;
import com.eta.modules.sys.mapper.SysDictMapper;
import com.eta.modules.sys.model.SysDict;
import com.eta.modules.sys.service.DictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 **/
@Service
public class DictServiceImpl implements DictService {

    @Autowired
    SysDictMapper dictMapper;

    @Override
    public List<SysDict> getAllWithPage(SysDict dict) {
        PageHelper.startPage(dict.getPage(), dict.getLimit());
        Example example = new Example(SysDict.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(dict.getDictName())) {
            criteria.andLike("dictName", "%" + dict.getDictName() + "%");
        }
        if (StringUtils.isNotBlank(dict.getDictLevel())) {
            criteria.andLike("dictLevel", "%" + dict.getDictLevel() + "%");
        }
        example.orderBy("id").desc();
        return dictMapper.selectByExample(example);
    }

    @Override
    public List<SysDict> getAllDict() {
        Example example = new Example(SysDict.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", 0);
        return dictMapper.selectByExample(example);
    }

    @Override
    public List<SysDict> listRootType() {
        Example example = new Example(SysDict.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("parentId", -1);
        return dictMapper.selectByExample(example);
    }

    @Override
    public List<SysDict> getDictByDictValue(String dictValue) {
        return dictMapper.getDictByDictValue(dictValue);
    }

    private ProcessResult update(SysDict dict) {
        int result = dictMapper.updateByPrimaryKeySelective(dict);
        if (result == CommonConstant.OPERATE_SUCCESS) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }

    private ProcessResult save(SysDict dict) {
        dict.setCreateTime(new Date());
        Integer parentId = dict.getParentId();
        String dictValue = dict.getDictValue();

        if (parentId == -1) {
            dict.setDictLevel(dictValue);
        } else {
            SysDict d = new SysDict();
            d.setId(parentId);
            SysDict sysDict = dictMapper.selectOne(d);
            dict.setDictLevel(sysDict.getDictLevel());
        }
        //保证根节点dictValue的唯一性
        SysDict d = new SysDict();
        d.setParentId(parentId);
        d.setDictValue(dictValue);
        SysDict sysDict = dictMapper.selectOne(d);
        if (sysDict != null) {
            return new ProcessResult(ProcessResult.ERROR, "字典类型为:" + dictValue + "节点已经存在");
        }
        int result = dictMapper.insert(dict);
        if (result == CommonConstant.OPERATE_SUCCESS) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }

    @Override
    public ProcessResult saveOrUpdate(SysDict dict) {
        if (dict.getId() != null) {//update
            return update(dict);
        } else {//insert
            return save(dict);
        }
    }

    @Override
    public SysDict getById(Integer id) {
        return dictMapper.selectByPrimaryKey(id);
    }

    @Override
    public ProcessResult deleteById(Integer id) {
        int result = dictMapper.deleteByPrimaryKey(id);
        if (result == CommonConstant.OPERATE_SUCCESS) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }

    @Override
    public ProcessResult batchDelete(Integer[] list) {
        Integer result = dictMapper.batchDelete(Arrays.asList(list));
        if (result == list.length) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }
}
