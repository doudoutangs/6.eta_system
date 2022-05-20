
package com.eta.modules.sys.controller;

import com.eta.core.result.JsonResult;
import com.eta.core.result.PageResult;
import com.eta.modules.sys.model.SysDealLog;
import com.eta.modules.sys.model.SysUser;
import com.eta.modules.sys.service.SysLogService;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/sys/log")
public class LogController {

    @Autowired
    private SysLogService sysLogService;

    @GetMapping("/list")
    public ModelAndView list() {
        return new ModelAndView("/modules/sys/log/list");
    }

    @GetMapping
    public PageResult<SysDealLog> getAll(SysDealLog sysDealLog,
                                         @Param("logDate") String logDate,
                                         String keyword,
                                         HttpServletRequest request) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
        if (sysUser == null) {
            new PageResult();
        }
        List<SysDealLog> logList = sysLogService.getListByPage(sysDealLog, keyword, logDate, sysUser);
        return new PageResult(new PageInfo<SysDealLog>(logList));
    }

    @GetMapping("/export")
    public JsonResult<Integer> downLoginLogExcel(HttpServletResponse response,
                                                 @Param("logDate") String logDate,
                                                 String keyword,
                                                 HttpServletRequest request) {
        try {
            SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
            if (sysUser == null) {
                new JsonResult<Integer>(1, "用户未登录！");
            }
            List<SysDealLog> logList = sysLogService.getList(null, keyword, logDate, sysUser);
//            FileUtil.downloadFile(response, sysLogService.getExcel(logList));
            return new JsonResult<>(0);
        } catch (Exception e) {
            return new JsonResult<>(e);
        }
    }


}
