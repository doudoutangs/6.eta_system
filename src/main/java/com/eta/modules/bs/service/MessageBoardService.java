package com.eta.modules.bs.service;

import com.eta.core.entity.ProcessResult;
import com.eta.modules.bs.model.MessageBoard;

import java.util.List;

public interface MessageBoardService {

    List<MessageBoard> getAllWithPage(MessageBoard param);

    List<MessageBoard> getAll();

    ProcessResult saveOrUpdate(MessageBoard param);

    MessageBoard getById(Integer id);

    ProcessResult deleteById(Integer id);

    ProcessResult batchDelete(Integer[] list);

}
