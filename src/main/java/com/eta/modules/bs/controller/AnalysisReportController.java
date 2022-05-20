package com.eta.modules.bs.controller;

import com.eta.core.annotation.SysLog;
import com.eta.core.entity.ProcessResult;
import com.eta.core.result.PageResult;
import com.eta.modules.bs.model.AnalysisReport;
import com.eta.modules.bs.service.AnalysisReportService;
import com.eta.modules.sys.model.SysUser;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 分析报告管理
 */
@RestController
@RequestMapping("/bs/analysisReport")
public class AnalysisReportController {

    @Autowired
    private AnalysisReportService analysisReportService;

    static String pathPrefix = "/modules/bs/analysisReport/";

    @GetMapping("/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView(pathPrefix + "list");
        return modelAndView;
    }

    @GetMapping("/form")
    public ModelAndView form() {
        ModelAndView modelAndView = new ModelAndView(pathPrefix + "form");
        return modelAndView;
    }

    @GetMapping("")
    public PageResult<AnalysisReport> getAll(AnalysisReport param) {
        List<AnalysisReport> list = analysisReportService.getAllWithPage(param);
        return new PageResult(new PageInfo<>(list)).setCode(0);
    }


    @SysLog("保存分析报告")
    @PostMapping(value = "/saveOrUpdate")
    @RequiresPermissions(value = {"bs:analysisReport:edit", "sys:bs:add"}, logical = Logical.OR)
    public ProcessResult saveOrUpdate(AnalysisReport param, HttpServletRequest request) {
        try {
            SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
            Integer userId = sysUser.getId();
            param.setUserId(userId);

            ProcessResult result = analysisReportService.saveOrUpdate(param);
            return result;
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }

    @GetMapping(value = "/view/{id}")
    public ProcessResult view(@PathVariable Integer id) {
        AnalysisReport param = analysisReportService.getById(id);
        ProcessResult processResult = new ProcessResult();
        processResult.setData(param);
        return processResult;
    }

    @SysLog("删除分析报告")
    @DeleteMapping(value = "/delete/{id}")
    @RequiresPermissions("bs:analysisReport:delete")
    public ProcessResult delete(@PathVariable Integer id) {
        try {
            return analysisReportService.deleteById(id);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }


    @SysLog("批量删除")
    @DeleteMapping(value = "/batchDelete")
    @RequiresPermissions("bs:analysisReport:batchDelete")
    public ProcessResult batchDelete(@RequestParam("ids[]") Integer[] ids) {
        try {
            return analysisReportService.batchDelete(ids);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }

}
