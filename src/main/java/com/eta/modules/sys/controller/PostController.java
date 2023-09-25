package com.eta.modules.sys.controller;

import com.eta.core.annotation.SysLog;
import com.eta.core.entity.ProcessResult;
import com.eta.core.result.PageResult;
import com.eta.modules.sys.model.SysPost;
import com.eta.modules.sys.model.SysUser;
import com.eta.modules.sys.service.PostService;
import com.github.pagehelper.PageInfo;
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
 * @Date: 2023/9/25 14:31
 */
@RestController
@RequestMapping("/sys/post")
public class PostController {

    @Autowired
    PostService postService;
    static String pathPrefix = "/modules/sys/post/";

    @GetMapping("/list")
    public ModelAndView list() {
        return new ModelAndView(pathPrefix + "list");
    }

    @GetMapping("")
    public PageResult<SysPost> getAll(SysPost sysUser) {
        List<SysPost> postList = postService.getAllWithPage(sysUser);
        return new PageResult(new PageInfo<>(postList)).setCode(0);
    }

    @SysLog("保存岗位")
    @PostMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"sys:post:edit", "sys:post:add"}, logical = Logical.OR)
    public ProcessResult saveOrUpdate(SysPost sysPost, HttpServletRequest request) {
        try {
            SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
            if (sysPost.getPostId() != null) {
                sysPost.setUpdateTime(new Date());
                sysPost.setUpdateBy(sysUser.getUsername());
            } else {
                sysPost.setCreateBy(sysUser.getUsername());
                sysPost.setCreateTime(new Date());
            }
            return postService.saveOrUpdate(sysPost);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }

    @GetMapping("/view/{id}")
    public ProcessResult view(@PathVariable Integer id) {
        SysPost sysPost = postService.getById(id);
        ProcessResult processResult = new ProcessResult();
        processResult.setData(sysPost);
        return processResult;
    }

    @SysLog("删除岗位")
    @DeleteMapping("/delete/{id}")
    @RequiresPermissions("sys:post:delete")
    public ProcessResult delete(@PathVariable Integer id) {
        try {
            postService.deleteById(id);
            return new ProcessResult();
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }

    @GetMapping("/form")
    public ModelAndView form() {
        return new ModelAndView(pathPrefix + "form");
    }

    @SysLog("批量删除用户")
    @DeleteMapping("/batchDelete")
    @RequiresPermissions("sys:post:batchDel")
    public ProcessResult batchDelete(@RequestParam("ids[]") Integer[] ids) {
        try {
            postService.batchDelete(ids);
            return new ProcessResult();
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }

}
