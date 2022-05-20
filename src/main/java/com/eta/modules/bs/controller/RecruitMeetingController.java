
package com.eta.modules.bs.controller;

import com.eta.core.annotation.SysLog;
import com.eta.core.entity.ProcessResult;
import com.eta.core.result.PageResult;
import com.eta.modules.bs.model.RecruitMeeting;
import com.eta.modules.bs.service.MajorService;
import com.eta.modules.bs.service.RecruitMeetingService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/bs/recruitMeeting")
public class RecruitMeetingController {

    @Autowired
    private RecruitMeetingService recruitMeetingService;

    @Autowired
    private MajorService majorService;
    static String pathPrefix = "/modules/bs/recruitMeeting/";

    @GetMapping("/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView(pathPrefix + "list");
        return modelAndView;
    }

    @GetMapping("/form")
    public ModelAndView form() {
        ModelAndView modelAndView = new ModelAndView(pathPrefix + "form");
        modelAndView.addObject("majorList", majorService.getAll());

        return modelAndView;
    }

    @GetMapping("")
    public PageResult<RecruitMeeting> getAll(RecruitMeeting param) {
        List<RecruitMeeting> list = recruitMeetingService.getAllWithPage(param);
        return new PageResult(new PageInfo<>(list)).setCode(0);
    }


    @SysLog("保存招聘信息")
    @PostMapping(value = "/saveOrUpdate")
    @RequiresPermissions(value = {"bs:recruitMeeting:edit", "bs:recruitMeeting:add"}, logical = Logical.OR)
    public ProcessResult saveOrUpdate(RecruitMeeting param, HttpServletRequest request) {
        try {
            return recruitMeetingService.saveOrUpdate(param);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }

    @GetMapping(value = "/view/{id}")
    public ProcessResult view(@PathVariable Integer id) {
        RecruitMeeting dict = recruitMeetingService.getById(id);
        ProcessResult processResult = new ProcessResult();
        processResult.setData(dict);
        return processResult;
    }

    @SysLog("删除招聘信息")
    @DeleteMapping(value = "/delete/{id}")
    @RequiresPermissions("bs:recruitMeeting:delete")
    public ProcessResult delete(@PathVariable Integer id) {
        try {
            return recruitMeetingService.deleteById(id);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }


    @SysLog("批量删除")
    @DeleteMapping(value = "/batchDelete")
    @RequiresPermissions("bs:recruitMeeting:batchDel")
    public ProcessResult batchDelete(@RequestParam("ids[]") Integer[] ids) {
        try {
            return recruitMeetingService.batchDelete(ids);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }
}
