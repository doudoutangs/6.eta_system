package com.eta.modules.bs.mapper;

import com.eta.core.mapper.MyMapper;
import com.eta.modules.bs.model.Notice;

import java.util.List;

public interface NoticeMapper extends MyMapper<Notice> {
    Integer batchDelete(List<Integer> list);
}