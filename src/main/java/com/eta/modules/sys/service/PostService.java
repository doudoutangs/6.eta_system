package com.eta.modules.sys.service;

import com.eta.core.entity.ProcessResult;
import com.eta.modules.sys.model.SysPost;

import java.util.List;

/**
 *
 *
 *
 **/
public interface PostService {

    List<SysPost> getAllWithPage(SysPost sysPost);

    ProcessResult saveOrUpdate(SysPost sysPost);

    SysPost getById(Integer id);

    ProcessResult deleteById(Integer id);

    ProcessResult batchDelete(Integer[] list);

    List<SysPost> getAllPost();

}
