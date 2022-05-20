package com.eta.core.exception;

import com.eta.core.entity.ProcessResult;
import com.eta.core.util.WebUtil;
import com.eta.modules.sys.mapper.SysLogMapper;
import com.eta.modules.sys.model.SysLog;
import com.eta.modules.sys.model.SysUser;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class MyExceptionResolver implements HandlerExceptionResolver {
	@Autowired
	private SysLogMapper sysLogMapper;
	public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler, Exception ex) {

		SysUser sysUser=(SysUser)request.getSession().getAttribute("user");
		//将异常日志保存到数据库
		SysLog sysLog=new SysLog();
		sysLog.setLogLevel(40000);
		sysLog.setCreateTime(new Date());
		sysLog.setUserId(sysUser.getId());
		sysLog.setUsername(sysUser.getUsername());
		sysLog.setUrl(request.getRequestURI());
		sysLog.setResult(ex.toString());
		sysLogMapper.insert(sysLog);
		String url = "/toError";
		String message = ex.getMessage();
		if(ex instanceof AuthorizationException){
			url = "/noAuth";
			message="无法访问未授权的功能";
		}
		// ajax请求,则返回异常提示
		if (isAjax(request)) {
			response.setStatus(HttpStatus.OK.value());
			WebUtil.renderJson(new ProcessResult(ProcessResult.ERROR,message), response);
			return null;
		}
		// 其他Http请求 直接返回错误页面
		else {
			message = ex.getMessage();
			request.setAttribute("message", message);
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			try {
				request.getRequestDispatcher(url).forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new ModelAndView();
	}

	private boolean isAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}
}
