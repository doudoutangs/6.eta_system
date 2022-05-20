package com.eta.modules.bs.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.eta.core.entity.ProcessResult;
import com.eta.modules.bs.mapper.SchoolClassMapper;
import com.eta.modules.bs.mapper.StudentMapper;
import com.eta.modules.bs.mapper.TestAnswerMapper;
import com.eta.modules.bs.model.SchoolClass;
import com.eta.modules.bs.model.StatisVO;
import com.eta.modules.bs.model.Student;
import com.eta.modules.bs.model.TestAnswer;
import com.eta.modules.bs.service.TestAnswerService;
import com.eta.modules.constant.CommonConstant;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sugar on 2021/11/25.
 */
@Service
public class TestAnswerServiceImpl implements TestAnswerService {

    @Autowired
    TestAnswerMapper testAnswerMapper;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    SchoolClassMapper schoolClassMapper;

    @Override
    public List<TestAnswer> getAllWithPage(TestAnswer param) {
        PageHelper.startPage(param.getPage(), param.getLimit());
        Example example = new Example(TestAnswer.class);
        Example.Criteria criteria = example.createCriteria();
        example.orderBy("id").desc();
        return testAnswerMapper.selectByExample(example);
    }

    @Override
    public List<TestAnswer> query(TestAnswer param) {
        PageHelper.startPage(param.getPage(), param.getLimit());

        return testAnswerMapper.query(param);
    }

    @Override
    public List<TestAnswer> getAll() {
        return testAnswerMapper.selectAll();
    }

    @Override
    public ProcessResult saveOrUpdate(TestAnswer param) {
        int result = 0;
        JSONObject data = new JSONObject();

        TestAnswer answer = testAnswerMapper.selectByPrimaryKey(param.getId());
        if (answer != null) {//update
            data.put("id", answer.getId());
            param.setId(answer.getId());
            result = testAnswerMapper.updateByPrimaryKeySelective(param);
        } else {//insert
            Student student = studentMapper.selectByPrimaryKey(param.getUserId());
            SchoolClass schoolClass = schoolClassMapper.selectByPrimaryKey(student.getClassId());
            param.setCreateTime(new Date());
            param.setVisitYear(schoolClass.getTerm());
            result = testAnswerMapper.insert(param);
            data.put("id", param.getId());
        }
        if (result == CommonConstant.OPERATE_SUCCESS) {
            return new ProcessResult(data);
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }

    @Override
    public TestAnswer getById(Integer id) {
        return testAnswerMapper.selectByPrimaryKey(id);
    }

    @Override
    public TestAnswer getByUserId(Integer userId) {
        return testAnswerMapper.getByUserId(userId);
    }

    @Override
    public ProcessResult deleteById(Integer id) {
        Example example = new Example(SchoolClass.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("teacherId", id);
        int result = testAnswerMapper.deleteByPrimaryKey(id);
        if (result == CommonConstant.OPERATE_SUCCESS) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }

    @Override
    public ProcessResult batchDelete(Integer[] list) {
        Integer result = 0;
        for (Integer id : list) {
            TestAnswer param = new TestAnswer();
            param.setId(id);
            Example example = new Example(SchoolClass.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("teacherId", id);
        }
        result = testAnswerMapper.batchDelete(Arrays.asList(list));
        if (result == list.length) {
            return new ProcessResult();
        } else {
            return new ProcessResult(ProcessResult.ERROR, "操作失败");
        }
    }

    @Override
    public Long statisticsByYear(String year) {
        return testAnswerMapper.statisticsByYear(year);
    }

    @Override
    public JSONObject statistics(List<String> yearList) {
        TestAnswer answer1 = new TestAnswer();
        answer1.setYearList(yearList);
        //选择直接就业
        TestAnswer answer2 = new TestAnswer();
        answer2.setSubjectId2("1");
        answer2.setYearList(yearList);
        List<StatisVO> list1 = testAnswerMapper.statisticsGroupByVisitYear(answer1);
        List<StatisVO> list2 = testAnswerMapper.statisticsGroupByVisitYear(answer2);
        List<Long> countList = list1.stream().map(StatisVO::getTotal).collect(Collectors.toList());
        List<Long> countWorkList = list2.stream().map(StatisVO::getTotal).collect(Collectors.toList());
        JSONObject json = new JSONObject();
        json.put("yearList", yearList);
        json.put("countList", countList);//每个年份参加问卷调查的人数
        json.put("countWorkList", countWorkList);//每个年份参加问卷调查选择就业的人数
        return json;
    }
}
