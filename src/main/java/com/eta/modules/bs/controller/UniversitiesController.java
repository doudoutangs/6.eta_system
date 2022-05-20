
package com.eta.modules.bs.controller;

import com.eta.core.annotation.SysLog;
import com.eta.core.entity.ProcessResult;
import com.eta.core.result.PageResult;
import com.eta.modules.bs.model.Major;
import com.eta.modules.bs.model.OrgTree;
import com.eta.modules.bs.model.Universities;
import com.eta.modules.bs.service.MajorService;
import com.eta.modules.bs.service.UniversitiesService;
import com.eta.modules.constant.DictConstant;
import com.eta.modules.sys.service.DictService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 学院管理
 */
@RestController
@RequestMapping("/bs/universities")
public class UniversitiesController {

    @Autowired
    private UniversitiesService universitiesService;
    @Autowired
    MajorService majorService;

    @Autowired
    private DictService paramService;

    static String pathPrefix = "/modules/bs/universities/";

    @GetMapping("/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView(pathPrefix + "list");
        modelAndView.addObject("cityList", paramService.getDictByDictValue(DictConstant.CITY));
        return modelAndView;
    }

    @GetMapping("/form")
    public ModelAndView form() {
        ModelAndView modelAndView = new ModelAndView(pathPrefix + "form");
        modelAndView.addObject("cityList", paramService.getDictByDictValue(DictConstant.CITY));
        return modelAndView;
    }

    @GetMapping("")
    public PageResult<Universities> getAll(Universities param) {
        List<Universities> list = universitiesService.getAllWithPage(param);
        return new PageResult(new PageInfo<>(list)).setCode(0);
    }


    @SysLog("保存院系")
    @PostMapping(value = "/saveOrUpdate")
    public ProcessResult saveOrUpdate(Universities param, HttpServletRequest request) {
        try {
            ProcessResult result = universitiesService.saveOrUpdate(param);
            return result;
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }

    @GetMapping(value = "/view/{id}")
    public ProcessResult view(@PathVariable String id) {
        Universities param = universitiesService.getById(id);
        ProcessResult processResult = new ProcessResult();
        processResult.setData(param);
        return processResult;
    }

    @SysLog("删除院系")
    @DeleteMapping(value = "/delete/{id}")
    public ProcessResult delete(@PathVariable String id) {
        try {
            List<Major> childList = majorService.getChildList(id);
            if (childList.size() > 0) {
                return new ProcessResult(ProcessResult.ERROR, "请先删除院系下的专业");

            }
            return universitiesService.deleteById(id);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }


    @SysLog("批量删除")
    @DeleteMapping(value = "/batchDelete")
    public ProcessResult batchDelete(@RequestParam("ids[]") String[] ids) {
        try {
            for (String id : ids) {
                List<Major> childList = majorService.getChildList(id);
                if (childList.size() > 0) {
                    return new ProcessResult(ProcessResult.ERROR, "请先删除院系下的专业");
                }
            }
            return universitiesService.batchDelete(ids);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }
    @RequestMapping(value = "getAllOrg", method = RequestMethod.GET)
    public OrgTree getAllOrg() {
        List<OrgTree> list = universitiesService.getAllOrg();
        OrgTree orgTree = new OrgTree();
        orgTree.setName("石家庄学院");
        orgTree.setId("-1");
        orgTree.setOpen(true);
        orgTree.setChildren(list);
        return orgTree;
    }

}
