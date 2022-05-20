
package com.eta.modules.bs.controller;

import com.eta.core.annotation.SysLog;
import com.eta.core.entity.ProcessResult;
import com.eta.core.result.PageResult;
import com.eta.modules.bs.model.MessageBoard;
import com.eta.modules.bs.service.MajorService;
import com.eta.modules.bs.service.MessageBoardService;
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
@RequestMapping("/bs/messageBoard")
public class MessageBoardController {

    @Autowired
    private MessageBoardService messageBoardService;

    @Autowired
    private MajorService majorService;
    static String pathPrefix = "/modules/bs/messageBoard/";

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
    public PageResult<MessageBoard> getAll(MessageBoard param) {
        List<MessageBoard> list = messageBoardService.getAllWithPage(param);
        return new PageResult(new PageInfo<>(list)).setCode(0);
    }


    @SysLog("保存公告")
    @PostMapping(value = "/saveOrUpdate")
    @RequiresPermissions(value = {"bs:messageBoard:edit", "bs:messageBoard:add"}, logical = Logical.OR)
    public ProcessResult saveOrUpdate(MessageBoard param, HttpServletRequest request) {
        try {
            SysUser sysUser = (SysUser) request.getSession().getAttribute("user");

            param.setUserId(sysUser.getId());
            return messageBoardService.saveOrUpdate(param);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }

    @GetMapping(value = "/view/{id}")
    public ProcessResult view(@PathVariable Integer id) {
        MessageBoard param = messageBoardService.getById(id);
        ProcessResult processResult = new ProcessResult();
        processResult.setData(param);
        return processResult;
    }

    @SysLog("删除公告")
    @DeleteMapping(value = "/delete/{id}")
    @RequiresPermissions("bs:messageBoard:delete")
    public ProcessResult delete(@PathVariable Integer id) {
        try {
            return messageBoardService.deleteById(id);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }

    @SysLog("批量删除")
    @DeleteMapping(value = "/batchDelete")
    @RequiresPermissions("bs:messageBoard:batchDel")
    public ProcessResult batchDelete(@RequestParam("ids[]") Integer[] ids) {
        try {
            return messageBoardService.batchDelete(ids);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage().toString());
        }
    }
}
