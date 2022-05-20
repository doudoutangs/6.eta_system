
package com.eta.modules.bs.controller;

import com.eta.core.annotation.SysLog;
import com.eta.core.entity.ProcessResult;
import com.eta.core.result.PageResult;
import com.eta.modules.bs.model.SchoolClass;
import com.eta.modules.bs.model.Student;
import com.eta.modules.bs.service.MajorService;
import com.eta.modules.bs.service.SchoolClassService;
import com.eta.modules.bs.service.StudentService;
import com.eta.modules.bs.service.TeacherService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 班级管理
 */
@RestController
@RequestMapping("/bs/schoolClass")
public class SchoolClassController {

    @Autowired
    private SchoolClassService schoolClassService;

    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private MajorService majorService;
    static String pathPrefix = "/modules/bs/schoolClass/";

    @GetMapping("/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView(pathPrefix + "list");
        modelAndView.addObject("majorList", majorService.getAll());

        return modelAndView;
    }

    @GetMapping("/form")
    public ModelAndView form() {
        ModelAndView modelAndView = new ModelAndView(pathPrefix + "form");
        modelAndView.addObject("majorList", majorService.getAll());
        modelAndView.addObject("teacherList", teacherService.getAll());

        return modelAndView;
    }

    @GetMapping("")
    public PageResult<SchoolClass> getAll(SchoolClass param) {
        List<SchoolClass> list = schoolClassService.getAllWithPage(param);
        return new PageResult(new PageInfo<>(list)).setCode(0);
    }


    @SysLog("保存班级")
    @PostMapping(value = "/saveOrUpdate")
    @RequiresPermissions(value = {"bs:schoolClass:edit", "bs:schoolClass:add"}, logical = Logical.OR)
    public ProcessResult saveOrUpdate(SchoolClass param, HttpServletRequest request) {
        try {
            schoolClassService.saveOrUpdate(param);
            return new ProcessResult();
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }

    @GetMapping(value = "/view/{id}")
    public ProcessResult view(@PathVariable String id) {
        SchoolClass dict = schoolClassService.getById(id);
        ProcessResult processResult = new ProcessResult();
        processResult.setData(dict);
        return processResult;
    }

    @SysLog("删除班级")
    @DeleteMapping(value = "/delete/{id}")
    @RequiresPermissions("bs:schoolClass:delete")
    public ProcessResult delete(@PathVariable String id) {
        try {
            List<Student> childList = studentService.getChildList(id);
            if (childList.size() > 0) {
                return new ProcessResult(ProcessResult.ERROR, "请先删除班级下的学生");
            }
            return schoolClassService.deleteById(id);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }

    @SysLog("批量删除")
    @DeleteMapping(value = "/batchDelete")
    @RequiresPermissions("bs:schoolClass:batchDel")
    public ProcessResult batchDelete(@RequestParam("ids[]") String[] ids) {
        try {
            for (String id : ids) {
                List<Student> childList = studentService.getChildList(id);
                if (childList.size() > 0) {
                    return new ProcessResult(ProcessResult.ERROR, "请先删除班级下的学生");
                }
            }
            return schoolClassService.batchDelete(ids);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }
}
