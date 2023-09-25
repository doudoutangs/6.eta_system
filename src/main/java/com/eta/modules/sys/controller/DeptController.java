
package com.eta.modules.sys.controller;

import com.eta.core.annotation.SysLog;
import com.eta.core.entity.ProcessResult;
import com.eta.modules.sys.model.SysDept;
import com.eta.modules.sys.model.SysUser;
import com.eta.modules.sys.service.DeptService;
import com.eta.modules.sys.service.UserService;
import com.eta.modules.sys.vo.SysDeptSelectVo;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
/**
 * @author 553039957@qq.com
 * 1. gitcode主页： https://gitcode.net/tbb414 （推荐）
 * 2. github主页：https://github.com/doudoutangs
 * 3. gitee(码云)主页：https://gitee.com/spdoudoutang
 * @Date: 2023/9/25 14:30
 */
@RestController
@RequestMapping("/sys/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;
    @Autowired
    private UserService userService;
    static String pathPrefix = "/modules/sys/dept/";

    @GetMapping("/list")
    public ModelAndView list() {
        return new ModelAndView(pathPrefix + "list");
    }

    @PostMapping
    public List<SysDept> getAll() {
        return deptService.getAll();
    }

    @GetMapping("/treeList")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ProcessResult treeList() {
        ProcessResult result = new ProcessResult(deptService.treeList());
        return result;
    }

    @GetMapping("/treeSelectList")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<SysDeptSelectVo> treeSelectList() {
        return deptService.treeSelectList();
    }

    @GetMapping("/getDeptById/{id}")
    public ProcessResult getDeptById(@PathVariable Integer id) {
        ProcessResult result = new ProcessResult();
        result.setData(deptService.getDeptById(id));
        return result;
    }

    @SysLog("保存部门信息")
    @PostMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"sys:dept:edit", "sys:dept:add"}, logical = Logical.OR)
    public ProcessResult saveOrUpdate(SysDept sysDept, HttpServletRequest request) {
        try {
            SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
            if (sysDept.getId() != null) {
                sysDept.setUpdateTime(new Date());
                sysDept.setUpdateBy(sysUser.getUsername());
            } else {
                sysDept.setCreateBy(sysUser.getUsername());
                sysDept.setCreateTime(new Date());
            }
            return deptService.saveOrUpdate(sysDept);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }

    @SysLog("删除部门")
    @DeleteMapping("/delete/{id}")
    @RequiresPermissions("sys:dept:delete")
    public ProcessResult delete(@PathVariable Integer id) {
        try {
            List<SysUser> userList = userService.getByDeptId(id);
            if (userList.size() > 0) {
                return new ProcessResult(ProcessResult.ERROR, "请先删除部门下员工");
            }
            return deptService.deleteById(id);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }
}
