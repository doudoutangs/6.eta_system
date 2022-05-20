
package com.eta.modules.bs.controller;

import com.eta.core.annotation.SysLog;
import com.eta.core.entity.ProcessResult;
import com.eta.core.result.PageResult;
import com.eta.modules.bs.model.Notice;
import com.eta.modules.bs.service.MajorService;
import com.eta.modules.bs.service.NoticeService;
import com.eta.modules.sys.model.SysUser;
import com.github.pagehelper.PageInfo;
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
@RestController
@RequestMapping("/bs/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private MajorService majorService;
    static String pathPrefix = "/modules/bs/notice/";

    @GetMapping("/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView(pathPrefix + "list");
        return modelAndView;
    }

    @GetMapping("/form")
    public ModelAndView form() {
        ModelAndView modelAndView = new ModelAndView(pathPrefix + "form");
        modelAndView.addObject("majorList", majorService.getAll());

        return modelAndView;
    }

    @GetMapping("")
    public PageResult<Notice> getAll(Notice param) {
        List<Notice> list = noticeService.getAllWithPage(param);
        return new PageResult(new PageInfo<>(list)).setCode(0);
    }


    @SysLog("保存公告")
    @PostMapping(value = "/saveOrUpdate")
    @RequiresPermissions(value = {"bs:notice:edit", "bs:notice:add"}, logical = Logical.OR)
    public ProcessResult saveOrUpdate(Notice param, HttpServletRequest request) {
        try {
            SysUser sysUser = (SysUser) request.getSession().getAttribute("user");

            param.setUserId(sysUser.getId());
            return noticeService.saveOrUpdate(param);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }

    @GetMapping(value = "/view/{id}")
    public ProcessResult view(@PathVariable Integer id) {
        Notice param = noticeService.getById(id);
        ProcessResult processResult = new ProcessResult();
        processResult.setData(param);
        return processResult;
    }

    @SysLog("删除公告")
    @DeleteMapping(value = "/delete/{id}")
    @RequiresPermissions("bs:notice:delete")
    public ProcessResult delete(@PathVariable Integer id) {
        try {
            return noticeService.deleteById(id);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }

    @SysLog("批量删除")
    @DeleteMapping(value = "/batchDelete")
    @RequiresPermissions("bs:notice:batchDel")
    public ProcessResult batchDelete(@RequestParam("ids[]") Integer[] ids) {
        try {
            return noticeService.batchDelete(ids);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }
}
