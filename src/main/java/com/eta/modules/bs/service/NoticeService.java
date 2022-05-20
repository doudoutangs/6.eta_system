package com.eta.modules.bs.service;

import com.eta.core.entity.ProcessResult;
import com.eta.modules.bs.model.Notice;

import java.util.List;

public interface NoticeService {

    List<Notice> getAllWithPage(Notice param);

    List<Notice> getAll();

    ProcessResult saveOrUpdate(Notice param);

    Notice getById(Integer id);

    ProcessResult deleteById(Integer id);

    ProcessResult batchDelete(Integer[] list);

}
