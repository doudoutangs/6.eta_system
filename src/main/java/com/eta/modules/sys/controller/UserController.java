
package com.eta.modules.sys.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.eta.core.annotation.SysLog;
import com.eta.core.entity.ProcessResult;
import com.eta.core.result.PageResult;
import com.eta.modules.sys.model.SysUser;
import com.eta.modules.sys.service.PostService;
import com.eta.modules.sys.service.RoleService;
import com.eta.modules.sys.service.UserService;
import com.eta.modules.sys.vo.SysRoleSelectVo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/sys/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PostService postService;
    static String pathPrefix = "/modules/sys/user/";

    @GetMapping("/list")
    public ModelAndView list() {
        return new ModelAndView(pathPrefix + "list");
    }

    @GetMapping("/info")
    public ModelAndView info(ModelMap map, HttpServletRequest request) {
        SysUser vo = (SysUser) request.getSession().getAttribute("user");
        if (vo != null) {
            map.put("user", vo);
        }
        return new ModelAndView("/modules/sys/set/info");
    }

    @GetMapping("/form/{deptId}/{id}")
    public ModelAndView form(@PathVariable("deptId") Integer deptId, @PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView(pathPrefix + "form");
        modelAndView.addObject("deptId", deptId);
        List<SysRoleSelectVo> sysRoleSelectVos = roleService.getAllRoles();
        String checkedRole = "[]";
        if (id != null && id.intValue() != 0) {//表示编辑状态
            List<Integer> list = roleService.findByUserId(id);
            if (list != null && !list.isEmpty()) {
                checkedRole = JSON.toJSONString(list);
            }
        }
        modelAndView.addObject("postList", postService.getAllPost());
        modelAndView.addObject("roleList", JSON.parseArray(sysRoleSelectVos.toString()));
        modelAndView.addObject("roleId", sysRoleSelectVos.get(0).getValue());
        modelAndView.addObject("checkedRole", checkedRole);
        modelAndView.addObject("defaultRole", checkedRole.replace("[", "").replace("]", ""));
        return modelAndView;
    }


    @GetMapping("/userlist/{roleId}")
    public PageResult<SysUser> userlist(@PathVariable("roleId") Integer roleId) {
        List<SysUser> users = userService.selectUserListByRoleId(roleId);
        return new PageResult(new PageInfo<SysUser>(users)).setCode(0);
    }


    @GetMapping("")
    public PageResult<SysUser> getAll(SysUser sysUser, String keyword) {
        List<SysUser> userInfoList = userService.getAll(sysUser, keyword);
        return new PageResult(new PageInfo<SysUser>(userInfoList)).setCode(0);
    }


    @SysLog("保存用户信息")
    @PostMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"sys:user:edit", "sys:user:add"}, logical = Logical.OR)
    public ProcessResult saveOrUpdate(SysUser sysUser) {
        try {
            ProcessResult result = userService.saveOrUpdate(sysUser);
            return result;
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }

    @GetMapping("/view/{id}")
    public ProcessResult view(@PathVariable Integer id) {
        SysUser userInfo = userService.getById(id);
        ProcessResult processResult = new ProcessResult();
        processResult.setData(userInfo);
        return processResult;
    }

    @SysLog("删除用户")
    @DeleteMapping("/delete/{id}")
    @RequiresPermissions("sys:user:delete")
    public ProcessResult delete(@PathVariable Integer id) {
        try {
            return userService.deleteById(id);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }

    @SysLog("批量删除用户")
    @PostMapping("/batchDelete")
    @RequiresPermissions("sys:user:batchDel")
    public ProcessResult batchDelete(@RequestParam("ids[]") Integer[] ids) {
        try {
            //----start----防止测试用户胡乱删除数据
            if (ids != null && ids.length > 0) {
                for (Integer id : ids) {
                    SysUser user = userService.getById(id);
                    if (user != null && "0".equals(user.getIsSysUser())) {
                        throw new Exception("系统用户不可删除");
                    }
                }
            }
            //-----end-----防止测试用户胡乱删除数据
            return userService.batchDelete(ids);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }
}
