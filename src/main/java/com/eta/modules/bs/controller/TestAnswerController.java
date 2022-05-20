
package com.eta.modules.bs.controller;

import com.alibaba.fastjson.JSONObject;
import com.eta.core.annotation.SysLog;
import com.eta.core.entity.ProcessResult;
import com.eta.core.result.PageResult;
import com.eta.modules.bs.model.TestAnswer;
import com.eta.modules.bs.service.TestAnswerService;
import com.eta.modules.sys.model.SysRole;
import com.eta.modules.sys.model.SysUser;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * 问卷调查
 */
@Slf4j
@RestController
@RequestMapping("/bs/testAnswer")
public class TestAnswerController {

    @Autowired
    private TestAnswerService testAnswerService;
    static String pathPrefix = "/modules/bs/testAnswer/";

    @GetMapping("/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView(pathPrefix + "list");
        Long count = testAnswerService.statisticsByYear(null);
        modelAndView.addObject("count", count);

        return modelAndView;
    }
    @PostMapping("/statistics")
    public ProcessResult statistics(@RequestBody List<String> yearList) {
        log.info("入参：{}",yearList.toString());
        JSONObject data = testAnswerService.statistics(yearList);
        return new ProcessResult(data);
    }

    @GetMapping("/form")
    public ModelAndView form(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView(pathPrefix + "form");
        return modelAndView;
    }

    @GetMapping("/form/{id}")
    public ModelAndView formId(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView(pathPrefix + "form");
        TestAnswer param = testAnswerService.getById(id);
        modelAndView.addObject("answer", param);

        return modelAndView;
    }

    @GetMapping("")
    public PageResult<TestAnswer> getAll(TestAnswer param, HttpServletRequest request) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
        Set<SysRole> roles = sysUser.getRoles();
        for (SysRole role : roles) {
            if (role.getId() == 2) {
                param.setUserId(sysUser.getId());
            }
        }
        List<TestAnswer> list = testAnswerService.query(param);
        return new PageResult(new PageInfo<>(list)).setCode(0);
    }


    @SysLog("保存问卷调查结果")
    @PostMapping(value = "/saveOrUpdate")
    @RequiresPermissions(value = {"bs:testAnswer:edit", "bs:testAnswer:add"}, logical = Logical.OR)
    public ProcessResult saveOrUpdate(TestAnswer param, HttpServletRequest request) {
        try {
            SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
            Set<SysRole> roles = sysUser.getRoles();
            Integer roleId = null;
            for (SysRole role : roles) {
                if (role.getId() == 2) {
                    roleId = 2;
                }
            }
            if (roleId != 2) {
                return new ProcessResult(ProcessResult.ERROR, "只有学生才可以填写和编辑问卷调查");
            }
            Integer userId = sysUser.getId();
            if (param.getId() == null) {
                TestAnswer answer = testAnswerService.getByUserId(userId);
                if (answer != null) {
                    return new ProcessResult(ProcessResult.ERROR, "请勿重复填写问卷调查");
                }
            }
            param.setUserId(userId);
            return testAnswerService.saveOrUpdate(param);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }

    @GetMapping(value = "/view/{id}")
    public ProcessResult view(@PathVariable Integer id) {
        TestAnswer param = testAnswerService.getById(id);
        ProcessResult processResult = new ProcessResult();
        processResult.setData(param);
        return processResult;
    }

    /**
     * 查询当前用户已填报问卷调查
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/user")
    public ProcessResult user(HttpServletRequest request) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
        TestAnswer answer = testAnswerService.getByUserId(sysUser.getId());

        ProcessResult processResult = new ProcessResult();
        processResult.setData(answer);
        return processResult;
    }
}
