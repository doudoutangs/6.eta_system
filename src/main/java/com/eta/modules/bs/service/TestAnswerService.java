package com.eta.modules.bs.service;

import com.alibaba.fastjson.JSONObject;
import com.eta.core.entity.ProcessResult;
import com.eta.modules.bs.model.TestAnswer;

import java.util.List;

public interface TestAnswerService {

    List<TestAnswer> getAllWithPage(TestAnswer param);

    List<TestAnswer> query(TestAnswer param);

    List<TestAnswer> getAll();

    ProcessResult saveOrUpdate(TestAnswer param);

    TestAnswer getById(Integer id);

    TestAnswer getByUserId(Integer userId);

    ProcessResult deleteById(Integer id);

    ProcessResult batchDelete(Integer[] list);

    Long statisticsByYear(String year);

    JSONObject statistics(List<String> yearList);


}
