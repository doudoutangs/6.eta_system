
package com.eta.modules.bs.controller;

import com.eta.core.annotation.SysLog;
import com.eta.core.entity.ProcessResult;
import com.eta.core.result.PageResult;
import com.eta.modules.bs.model.Recruit;
import com.eta.modules.bs.service.MajorService;
import com.eta.modules.bs.service.RecruitService;
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
 *
 */
@RestController
@RequestMapping("/bs/recruit")
public class RecruitController {

    @Autowired
    private RecruitService recruitService;

    @Autowired
    private MajorService majorService;
    static String pathPrefix = "/modules/bs/recruit/";

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
    public PageResult<Recruit> getAll(Recruit param) {
        List<Recruit> list = recruitService.getAllWithPage(param);
        return new PageResult(new PageInfo<>(list)).setCode(0);
    }


    @SysLog("保存招聘信息")
    @PostMapping(value = "/saveOrUpdate")
    @RequiresPermissions(value = {"bs:recruit:edit", "bs:recruit:add"}, logical = Logical.OR)
    public ProcessResult saveOrUpdate(Recruit param, HttpServletRequest request) {
        try {
            SysUser sysUser = (SysUser) request.getSession().getAttribute("user");

            param.setUserId(sysUser.getId());
            recruitService.saveOrUpdate(param);
            return new ProcessResult();
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }

    @GetMapping(value = "/view/{id}")
    public ProcessResult view(@PathVariable Integer id) {
        Recruit param = recruitService.getById(id);
        ProcessResult processResult = new ProcessResult();
        processResult.setData(param);
        return processResult;
    }

    @SysLog("删除招聘信息")
    @DeleteMapping(value = "/delete/{id}")
    @RequiresPermissions("bs:recruit:delete")
    public ProcessResult delete(@PathVariable Integer id) {
        try {
            return recruitService.deleteById(id);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }


    @SysLog("批量删除")
    @DeleteMapping(value = "/batchDelete")
    @RequiresPermissions("bs:recruit:batchDel")
    public ProcessResult batchDelete(@RequestParam("ids[]") Integer[] ids) {
        try {
            return recruitService.batchDelete(ids);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }
}
