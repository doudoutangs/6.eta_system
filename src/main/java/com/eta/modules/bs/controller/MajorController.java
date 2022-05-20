
package com.eta.modules.bs.controller;

import com.eta.core.annotation.SysLog;
import com.eta.core.entity.ProcessResult;
import com.eta.core.result.PageResult;
import com.eta.modules.bs.model.Major;
import com.eta.modules.bs.model.SchoolClass;
import com.eta.modules.bs.service.MajorService;
import com.eta.modules.bs.service.SchoolClassService;
import com.eta.modules.bs.service.UniversitiesService;
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
 * 专业管理
 */
@RestController
@RequestMapping("/bs/major")
public class MajorController {

    @Autowired
    private MajorService majorService;

    @Autowired
    private DictService dictService;
    @Autowired
    private UniversitiesService universitiesService;
    @Autowired
    private SchoolClassService schoolClassService;
    static String pathPrefix = "/modules/bs/major/";

    @GetMapping("/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView(pathPrefix + "list");
        modelAndView.addObject("universitiesList", universitiesService.getAll());

        return modelAndView;
    }

    @GetMapping("/form")
    public ModelAndView form() {
        ModelAndView modelAndView = new ModelAndView(pathPrefix + "form");
        modelAndView.addObject("universitiesList", universitiesService.getAll());
        modelAndView.addObject("majorTypeList", dictService.getDictByDictValue(DictConstant.MAJOR_TYPE));

        return modelAndView;
    }

    @GetMapping("/form/{id}")
    public ModelAndView formId(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView(pathPrefix + "form");
        Major param = majorService.getById(id);
        modelAndView.addObject("major", param);
        modelAndView.addObject("majorTypeList", dictService.getDictByDictValue(DictConstant.MAJOR_TYPE));

        return modelAndView;
    }

    @GetMapping("")
    public PageResult<Major> getAll(Major param) {
        List<Major> list = majorService.getAllWithPage(param);
        return new PageResult(new PageInfo<>(list)).setCode(0);
    }


    @SysLog("保存专业")
    @PostMapping(value = "/saveOrUpdate")
    @RequiresPermissions(value = {"bs:major:edit", "bs:major:add"}, logical = Logical.OR)
    public ProcessResult saveOrUpdate(Major param, HttpServletRequest request) {
        try {
            return majorService.saveOrUpdate(param);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }

    @GetMapping(value = "/view/{id}")
    public ProcessResult view(@PathVariable String id) {
        Major param = majorService.getById(id);
        ProcessResult processResult = new ProcessResult();
        processResult.setData(param);
        return processResult;
    }

    @SysLog("删除专业")
    @DeleteMapping(value = "/delete/{id}")
    @RequiresPermissions("bs:major:delete")
    public ProcessResult delete(@PathVariable String id) {
        try {
            List<SchoolClass> childList = schoolClassService.getChildList(id);
            if (childList.size() > 0) {
                return new ProcessResult(ProcessResult.ERROR, "请先删除专业下的班级");
            }
            return majorService.deleteById(id);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }

    @SysLog("批量删除")
    @DeleteMapping(value = "/batchDelete")
    @RequiresPermissions("bs:major:batchDel")
    public ProcessResult batchDelete(@RequestParam("ids[]") String[] ids) {
        try {
            for (String id : ids) {
                List<SchoolClass> childList = schoolClassService.getChildList(id);
                if (childList.size() > 0) {
                    return new ProcessResult(ProcessResult.ERROR, "请先删除专业下的班级");
                }
            }
            return majorService.batchDelete(ids);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }
}
