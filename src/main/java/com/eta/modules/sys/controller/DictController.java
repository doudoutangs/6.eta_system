
package com.eta.modules.sys.controller;

import com.eta.core.annotation.SysLog;
import com.eta.core.entity.ProcessResult;
import com.eta.core.result.PageResult;
import com.eta.modules.sys.model.SysDict;
import com.eta.modules.sys.service.DictService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/sys/dict")
public class DictController {
    @Autowired
    private DictService dictService;
    static String pathPrefix = "/modules/sys/dict/";

    @GetMapping("/list")

    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView(pathPrefix + "list");
        modelAndView.addObject("dictTypeList", dictService.listRootType());
        return modelAndView;
    }

    @GetMapping("/form")
    public ModelAndView form() {
        ModelAndView modelAndView = new ModelAndView(pathPrefix + "form");
        modelAndView.addObject("dictTypeList", dictService.listRootType());
        modelAndView.addObject("parentId", "");

        return modelAndView;
    }

    @GetMapping("/form/{id}")
    public ModelAndView formId(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView(pathPrefix + "form");
        modelAndView.addObject("dictTypeList", dictService.listRootType());
        modelAndView.addObject("parentId", id+"");
        modelAndView.addObject("dict", dictService.getById(id));
        return modelAndView;
    }
    @GetMapping
    public PageResult<SysDict> getAll(SysDict dict) {
        dict.setLimit(9999);
        List<SysDict> list = dictService.getAllWithPage(dict);
        return new PageResult(list);
    }

    @GetMapping("/listRootType")
    public PageResult<SysDict> listRootType() {
        List<SysDict> list = dictService.listRootType();
        return new PageResult(new PageInfo(list));
    }

    @GetMapping("/listType")
    public PageResult<SysDict> listType(String dictValue) {
        List<SysDict> list = dictService.getDictByDictValue(dictValue);
        return new PageResult(new PageInfo(list));
    }

    @SysLog("保存字典")
    @PostMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"sys:dict:edit", "sys:dict:add"}, logical = Logical.OR)
    public ProcessResult saveOrUpdate(SysDict dict, HttpServletRequest request) {
        try {
            ProcessResult result = dictService.saveOrUpdate(dict);
            return result;
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }


    @GetMapping("/view/{id}")
    public ProcessResult view(@PathVariable Integer id) {
        SysDict dict = dictService.getById(id);
        ProcessResult processResult = new ProcessResult();
        processResult.setData(dict);
        return processResult;
    }

    @SysLog("删除字典")
    @DeleteMapping("/delete/{id}")
    @RequiresPermissions("sys:dict:delete")
    public ProcessResult delete(@PathVariable Integer id) {
        try {
            return dictService.deleteById(id);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }

    @SysLog("批量删除")
    @DeleteMapping("/batchDelete")
    @RequiresPermissions("sys:dict:batchDel")
    public ProcessResult batchDelete(@RequestParam("ids[]") Integer[] ids) {
        try {
            return dictService.batchDelete(ids);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }


}
