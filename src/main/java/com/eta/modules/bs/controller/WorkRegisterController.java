
package com.eta.modules.bs.controller;

import com.eta.core.annotation.SysLog;
import com.eta.core.entity.ProcessResult;
import com.eta.core.result.PageResult;
import com.eta.modules.bs.model.WorkRegister;
import com.eta.modules.bs.service.MajorService;
import com.eta.modules.bs.service.SchoolClassService;
import com.eta.modules.bs.service.UniversitiesService;
import com.eta.modules.bs.service.WorkRegisterService;
import com.eta.modules.constant.DictConstant;
import com.eta.modules.sys.model.SysRole;
import com.eta.modules.sys.model.SysUser;
import com.eta.modules.sys.service.DictService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 *
 */
@RestController
@RequestMapping("/bs/workRegister")
public class WorkRegisterController {

    @Autowired
    private WorkRegisterService workRegisterService;
    @Autowired
    private UniversitiesService universitiesService;
    @Autowired
    private SchoolClassService schoolClassService;
    @Autowired
    private DictService dictService;
    @Autowired
    private MajorService majorService;
    static String pathPrefix = "/modules/bs/workRegister/";

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
        modelAndView.addObject("majorList", majorService.getAll());
        modelAndView.addObject("cityList", dictService.getDictByDictValue(DictConstant.CITY));
        modelAndView.addObject("companyNatureList", dictService.getDictByDictValue(DictConstant.COMPANY_NATURE));
        return modelAndView;
    }

    @GetMapping("")
    public PageResult<WorkRegister> getAll(WorkRegister param, HttpServletRequest request) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
        Set<SysRole> roles = sysUser.getRoles();
        for (SysRole role : roles) {
            if (role.getId() == 2) {
                param.setStudentId(sysUser.getId());
            }
        }
        List<WorkRegister> list = workRegisterService.getAllWithPage(param);
        return new PageResult(new PageInfo<>(list)).setCode(0);
    }


    @SysLog("保存就业登记")
    @PostMapping(value = "/saveOrUpdate")
    @RequiresPermissions(value = {"bs:workRegister:edit", "bs:workRegister:add"}, logical = Logical.OR)
    public ProcessResult saveOrUpdate(WorkRegister param, HttpServletRequest request) {
        try {
            if(StringUtils.isEmpty(param.getRegisterDate())){
                return new ProcessResult(ProcessResult.ERROR, "登记日期不能为空");
            }
            SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
            param.setStudentId(sysUser.getId());
            Set<SysRole> roles = sysUser.getRoles();
            for (SysRole role : roles) {
                if (role.getId() != 2) {
                    return new ProcessResult(ProcessResult.ERROR, "只有学生才可以提交就业登记");
                }
            }
            return workRegisterService.saveOrUpdate(param);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }

    @GetMapping(value = "/view/{id}")
    public ProcessResult view(@PathVariable Integer id) {
        WorkRegister param = workRegisterService.getById(id);
        ProcessResult processResult = new ProcessResult();
        processResult.setData(param);
        return processResult;
    }

    @SysLog("删除就业登记")
    @DeleteMapping(value = "/delete/{id}")
    @RequiresPermissions("bs:workRegister:delete")
    public ProcessResult delete(@PathVariable Integer id) {
        try {
            return workRegisterService.deleteById(id);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }

    @SysLog("批量删除")
    @DeleteMapping(value = "/batchDelete")
    @RequiresPermissions("bs:workRegister:batchDel")
    public ProcessResult batchDelete(@RequestParam("ids[]") Integer[] ids) {
        try {
            return workRegisterService.batchDelete(ids);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }
}
