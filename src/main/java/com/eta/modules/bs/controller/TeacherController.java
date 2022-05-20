
package com.eta.modules.bs.controller;

import com.eta.core.annotation.SysLog;
import com.eta.core.entity.ProcessResult;
import com.eta.core.result.PageResult;
import com.eta.modules.bs.model.Teacher;
import com.eta.modules.bs.service.MajorService;
import com.eta.modules.bs.service.TeacherService;
import com.eta.modules.constant.DictConstant;
import com.eta.modules.sys.service.DictService;
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
@RequestMapping("/bs/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private DictService dictService;
    @Autowired
    private MajorService majorService;
    static String pathPrefix = "/modules/bs/teacher/";

    @GetMapping("/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView(pathPrefix + "list");
        modelAndView.addObject("cityList", dictService.getDictByDictValue(DictConstant.CITY));

        return modelAndView;
    }

    @GetMapping("/form")
    public ModelAndView form() {
        ModelAndView modelAndView = new ModelAndView(pathPrefix + "form");
        modelAndView.addObject("majorList", majorService.getAll());
        modelAndView.addObject("cityList", dictService.getDictByDictValue(DictConstant.CITY));
        return modelAndView;
    }

    @GetMapping("")
    public PageResult<Teacher> getAll(Teacher param) {
        List<Teacher> list = teacherService.getAllWithPage(param);
        return new PageResult(new PageInfo<>(list)).setCode(0);
    }


    @SysLog("保存老师")
    @PostMapping(value = "/saveOrUpdate")
    @RequiresPermissions(value = {"bs:teacher:edit", "bs:teacher:add"}, logical = Logical.OR)
    public ProcessResult saveOrUpdate(Teacher param, HttpServletRequest request) {
        try {
            teacherService.saveOrUpdate(param);
            return new ProcessResult();
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }

    @GetMapping(value = "/view/{id}")
    public ProcessResult view(@PathVariable Integer id) {
        Teacher dict = teacherService.getById(id);
        ProcessResult processResult = new ProcessResult();
        processResult.setData(dict);
        return processResult;
    }

    @SysLog("删除老师")
    @DeleteMapping(value = "/delete/{id}")
    @RequiresPermissions("bs:teacher:delete")
    public ProcessResult delete(@PathVariable Integer id) {
        try {

            return teacherService.deleteById(id);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }


    @SysLog("批量删除")
    @DeleteMapping(value = "/batchDelete")
    @RequiresPermissions("bs:teacher:batchDel")
    public ProcessResult batchDelete(@RequestParam("ids[]") Integer[] ids) {
        try {
            return teacherService.batchDelete(ids);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }
}
