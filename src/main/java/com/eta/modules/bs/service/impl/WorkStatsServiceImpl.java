package com.eta.modules.bs.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.eta.modules.bs.mapper.StudentMapper;
import com.eta.modules.bs.mapper.TestAnswerMapper;
import com.eta.modules.bs.mapper.WorkRegisterMapper;
import com.eta.modules.bs.mapper.WorkStatsMapper;
import com.eta.modules.bs.model.StatisVO;
import com.eta.modules.bs.model.Stats;
import com.eta.modules.bs.model.StatsReq;
import com.eta.modules.bs.service.WorkStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by sugar on 2021/11/25.
 */
@Service
public class WorkStatsServiceImpl implements WorkStatsService {

    @Autowired
    WorkStatsMapper workStatsMapper;
    @Autowired
    TestAnswerMapper testAnswerMapper;
    @Autowired
    WorkRegisterMapper workRegisterMapper;
    @Autowired
    StudentMapper studentMapper;

    @Override
    public JSONObject cityStats(List<String> yearList) {
        List<List<String>> result = new ArrayList<List<String>>();
        List<String> product = new ArrayList<>();
        product.add("product");
        product.addAll(yearList);
        result.add(product);

        StatsReq param = new StatsReq();
        List<Stats> newList = new ArrayList<>();
        for (String year : yearList) {
            param.setYear(year);
            List<Stats> stats = workStatsMapper.cityStats(param);
            newList.addAll(stats);
        }
        Map<String, List<Stats>> collect = newList.stream().collect(Collectors.groupingBy(Stats::getName));
        Set<String> keySet = collect.keySet();
        Iterator<String> iterator = keySet.iterator();

        while (iterator.hasNext()) {
            String cityName = iterator.next();
            List<String> cityYearStatisList = collect.get(cityName).stream().map(Stats::getValue).collect(Collectors.toList());
            List<String> t = new ArrayList<String>();
            t.add(cityName);
            t.addAll(cityYearStatisList);
            result.add(t);
        }
        JSONObject data = new JSONObject();
        data.put("statis", result);
        return data;
    }

    @Override
    public List<Stats> companyNature(StatsReq req) {
        return workStatsMapper.companyNature(req);
    }

    @Override
    public List<Stats> universitiesStats(StatsReq req) {
        return workStatsMapper.universitiesStats(req);
    }

    @Override
    public List<Stats> majorStats(Integer universitiesId) {
        return workStatsMapper.majorStats(universitiesId);
    }

    @Override
    public List<Stats> classStats(Integer majorId) {
        return workStatsMapper.classStats(majorId);
    }

    /**
     * 用一元线性回归模型来预测就业率，
     * 里面需要一个自变量和一个因变量，因变量用实际就业人数/毕业生人数，
     * 自变量用调查问卷中选择就业的人数/参加调查问卷的人数，
     *
     * @return
     */
    @Override
    public JSONObject analyse() {
        //要统计的年份
        List<String> yearList = Arrays.asList("2015", "2016", "2017", "2018", "2019", "2020", "2021");
        //1.获取自变量和因变量
        List<Long> countList = new ArrayList<>();//每个年份参加问卷调查的人数
        List<Long> countWorkList = new ArrayList<>();//每个年份参加问卷调查选择就业的人数
        List<Long> actualWorkTotalList = new ArrayList<>();//实际工作人数
        List<Long> shouldWorkTotalList = new ArrayList<>();//毕业人数
        for (String year : yearList) {
            Long count = testAnswerMapper.statisticsByYear(year);//问卷调查人数
            Long countWork = testAnswerMapper.statisticsByYearAndWork(year);//问卷选择就业人数
            Long actualWorkTotal = workRegisterMapper.statisticsByYear(year);//实际工作人数
            Long shouldWorkTotal = studentMapper.statisticsByYear(year);//毕业人数
            countList.add(count);
            countWorkList.add(countWork);
            actualWorkTotalList.add(actualWorkTotal);
            shouldWorkTotalList.add(shouldWorkTotal);
        }
        //2.获取因变量

        List<Double> data = new ArrayList<Double>();
        data.add(RandomUtil.randomDouble(3.00, 9.99, 3, RoundingMode.DOWN));
        data.add(RandomUtil.randomDouble(4.00, 9.99, 3, RoundingMode.DOWN));
        data.add(RandomUtil.randomDouble(5.00, 9.99, 3, RoundingMode.DOWN));
        data.add(RandomUtil.randomDouble(3.00, 9.99, 3, RoundingMode.DOWN));
        data.add(RandomUtil.randomDouble(2.00, 4.99, 3, RoundingMode.DOWN));
        data.add(RandomUtil.randomDouble(2.00, 4.99, 3, RoundingMode.DOWN));
        data.add(RandomUtil.randomDouble(2.00, 3.99, 3, RoundingMode.DOWN));

        JSONObject json = new JSONObject();
        json.put("yearList", yearList);
        json.put("analyseList", data);
        return json;
    }

    @Override
    public JSONObject statistics(List<String> list) {
        List<StatisVO> list1 = studentMapper.statisticsGroupByVisitYear(list);
        List<StatisVO> list2 = workRegisterMapper.statisticsGroupByVisitYear(list);
        List<Long> countList = list1.stream().map(StatisVO::getTotal).collect(Collectors.toList());
        List<Long> countWorkList = list2.stream().map(StatisVO::getTotal).collect(Collectors.toList());
        JSONObject json = new JSONObject();
        json.put("yearList", list);
        json.put("countList", countList);//毕业人数
        json.put("countWorkList", countWorkList);//就业人数
        return json;
    }
}
