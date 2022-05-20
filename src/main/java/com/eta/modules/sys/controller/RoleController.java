
package com.eta.modules.sys.controller;

import com.eta.core.annotation.SysLog;
import com.eta.core.entity.ProcessResult;
import com.eta.core.result.PageResult;
import com.eta.modules.sys.model.SysRole;
import com.eta.modules.sys.service.MenuService;
import com.eta.modules.sys.service.RoleService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/sys/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;
    static String pathPrefix = "/modules/sys/role/";

    @GetMapping("/form")
    public ModelAndView form() {
        return new ModelAndView(pathPrefix + "form");
    }


    @GetMapping("/auth/{roleId}")
    public ModelAndView auth(@PathVariable("roleId") Integer roleId) {
        ModelAndView modelAndView = new ModelAndView(pathPrefix + "authmanage");
        modelAndView.addObject("roleId", roleId);
        List<Integer> checkedIds = menuService.selectMenuListByRoleId(roleId);
        modelAndView.addObject("checkedKeys", checkedIds.toString());
        return modelAndView;
    }

    @GetMapping("/form/user/{roleId}")
    @RequiresPermissions("sys:role:userlist")
    public ModelAndView userForm(@PathVariable("roleId") Integer roleId) {
        ModelAndView modelAndView = new ModelAndView(pathPrefix + "userlist");
        modelAndView.addObject("roleId", roleId);
        return modelAndView;
    }

    @GetMapping("/list")
    public ModelAndView list() {
        return new ModelAndView(pathPrefix + "list");
    }

    @GetMapping
    public PageResult<SysRole> getAll(SysRole sysRole, String keyword, HttpServletRequest request) {
        List<SysRole> roleList = roleService.getAll(sysRole, keyword);
        return new PageResult(roleList);
    }

    @SysLog("保存角色")
    @PostMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"sys:role:edit", "sys:role:add"}, logical = Logical.OR)
    public ProcessResult saveOrUpdate(SysRole sysRole) {
        try {
            return roleService.saveOrUpdate(sysRole);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }

    @GetMapping("/view/{id}")
    public ProcessResult view(@PathVariable Integer id) {
        SysRole sysRole = roleService.getById(id);
        ProcessResult processResult = new ProcessResult();
        processResult.setData(sysRole);
        return processResult;
    }

    @SysLog("删除角色")
    @DeleteMapping("/delete/{id}")
    @RequiresPermissions("sys:role:delete")
    public ProcessResult delete(@PathVariable Integer id) {
        try {
            return roleService.deleteById(id);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }
}
