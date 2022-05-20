
package com.eta.modules.bs.controller;

import com.alibaba.fastjson.JSONObject;
import com.eta.core.entity.ProcessResult;
import com.eta.modules.bs.model.Stats;
import com.eta.modules.bs.model.StatsReq;
import com.eta.modules.bs.model.Universities;
import com.eta.modules.bs.service.UniversitiesService;
import com.eta.modules.bs.service.WorkStatsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@RestController
@RequestMapping("/bs/workStats")
public class WorkStatsController {

    @Autowired
    private UniversitiesService universitiesService;
    @Autowired
    private WorkStatsService workStatsService;
    static String pathPrefix = "/modules/bs/workStats/";

    @GetMapping("/city")
    public ModelAndView city() {
        ModelAndView modelAndView = new ModelAndView(pathPrefix + "stats");
        return modelAndView;
    }

    @GetMapping("/analyse")
    public ModelAndView analyse() {
        ModelAndView modelAndView = new ModelAndView(pathPrefix + "analyse");
        //一元线性回归模型来预测就业率，里面需要一个自变量和一个因变量，因变量用实际就业人数/毕业生人数，自变量用调查问卷中选择就业的人数/参加调查问卷的人数
        return modelAndView;
    }

    @GetMapping("/analyseTrend")
    public ProcessResult analyseTrend(HttpServletRequest request) {
        JSONObject analyse = workStatsService.analyse();
        return new ProcessResult(analyse);
    }

    @PostMapping("/statistics")
    public ProcessResult statistics(@RequestBody List<String> yearList) {
        JSONObject analyse = workStatsService.statistics(yearList);
        return new ProcessResult(analyse);
    }

    @PostMapping("/cityStats")
    public ProcessResult cityStats(@RequestBody List<String> yearList, HttpServletRequest request) {
        JSONObject data = workStatsService.cityStats(yearList);
        return new ProcessResult(data);
    }

    @GetMapping("/companyNature")
    public ProcessResult companyNature(StatsReq req, HttpServletRequest request) {
        List<Stats> list = workStatsService.companyNature(req);
        return new ProcessResult(list);
    }

    @GetMapping("/universitiesStats")
    public ProcessResult universitiesStats(StatsReq req, HttpServletRequest request) {
        List<Stats> list = workStatsService.universitiesStats(req);
        return new ProcessResult(list);
    }

    @GetMapping("/majorStats")
    public ProcessResult majorStats(Integer universitiesId, HttpServletRequest request) {
        List<Stats> list = workStatsService.majorStats(universitiesId);
        return new ProcessResult(list);
    }

    @GetMapping("/classStats")
    public ProcessResult classStats(Integer majorId, HttpServletRequest request) {
        List<Stats> list = workStatsService.classStats(majorId);
        return new ProcessResult(list);
    }

    @GetMapping("/universitiesList")
    public ProcessResult universitiesList() {
        List<Universities> list = universitiesService.getAll();
        List<String> collect = list.stream().map(Universities::getName).collect(Collectors.toList());
        return new ProcessResult(collect);
    }
}
