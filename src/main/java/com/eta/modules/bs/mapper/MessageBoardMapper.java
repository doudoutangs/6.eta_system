package com.eta.modules.bs.mapper;

import com.eta.core.mapper.MyMapper;
import com.eta.modules.bs.model.MessageBoard;

import java.util.List;

public interface MessageBoardMapper extends MyMapper<MessageBoard> {
    Integer batchDelete(List<Integer> list);

}