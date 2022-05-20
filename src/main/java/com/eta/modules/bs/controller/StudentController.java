
package com.eta.modules.bs.controller;

import com.eta.core.annotation.SysLog;
import com.eta.core.entity.ProcessResult;
import com.eta.core.result.PageResult;
import com.eta.modules.bs.model.Major;
import com.eta.modules.bs.model.Student;
import com.eta.modules.bs.model.StudentVO;
import com.eta.modules.bs.service.MajorService;
import com.eta.modules.bs.service.SchoolClassService;
import com.eta.modules.bs.service.StudentService;
import com.eta.modules.bs.service.UniversitiesService;
import com.eta.modules.constant.DictConstant;
import com.eta.modules.sys.service.DictService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "学生信息")
@RestController
@RequestMapping("/bs/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private SchoolClassService schoolClassService;

    @Autowired
    private DictService dictService;
    @Autowired
    private UniversitiesService universitiesService;
    @Autowired
    private MajorService majorService;
    static String pathPrefix = "/modules/bs/student/";

    @GetMapping("/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView(pathPrefix + "list");
        modelAndView.addObject("cityList", dictService.getDictByDictValue(DictConstant.CITY));
        modelAndView.addObject("schoolClassList", schoolClassService.getAll());
        modelAndView.addObject("majorList", majorService.getAll());
        modelAndView.addObject("universitiesList", universitiesService.getAll());
        String icon = "layui-icon-star-fill";
        modelAndView.addObject("defaultIcon", icon);
        modelAndView.addObject("parentId", "-1");
        return modelAndView;
    }

    @GetMapping("/form")
    public ModelAndView form() {
        ModelAndView modelAndView = new ModelAndView(pathPrefix + "form");
        modelAndView.addObject("schoolClassList", schoolClassService.getAll());
        modelAndView.addObject("majorList", majorService.getAll());
        modelAndView.addObject("universitiesList", universitiesService.getAll());
        modelAndView.addObject("cityList", dictService.getDictByDictValue(DictConstant.CITY));

        return modelAndView;
    }

    @GetMapping("")
    public PageResult<StudentVO> getAll(StudentVO param) {
        List<StudentVO> list = studentService.getAllWithPage(param);
        return new PageResult(new PageInfo<>(list)).setCode(0);
    }

    @ApiOperation("保存学生")
    @SysLog("保存学生")
    @PostMapping(value = "/saveOrUpdate")
    @RequiresPermissions(value = {"bs:student:edit", "bs:student:add"}, logical = Logical.OR)
    public ProcessResult saveOrUpdate(Student param, HttpServletRequest request) {
        try {
            ProcessResult result=  studentService.saveOrUpdate(param);
            return result;
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }

    @GetMapping(value = "/view/{id}")
    public ProcessResult view(@PathVariable Integer id) {
        Student dict = studentService.getById(id);
        ProcessResult processResult = new ProcessResult();
        processResult.setData(dict);
        return processResult;
    }

    @SysLog("删除学生")
    @DeleteMapping(value = "/delete/{id}")
    @RequiresPermissions("bs:student:delete")
    public ProcessResult delete(@PathVariable Integer id) {
        try {
            ProcessResult result = studentService.deleteById(id);
            return result;
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }


    @SysLog("批量删除")
    @DeleteMapping(value = "/batchDelete")
    @RequiresPermissions("bs:student:batchDel")
    public ProcessResult batchDelete(@RequestParam("ids[]") Integer[] ids) {
        try {
            ProcessResult result = studentService.batchDelete(ids);
            return result;
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }
}
