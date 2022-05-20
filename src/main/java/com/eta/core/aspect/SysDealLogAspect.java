package com.eta.core.aspect;

import com.google.gson.Gson;
import com.eta.core.annotation.SysLog;
import com.eta.core.util.HttpContextUtils;
import com.eta.core.util.IpUtils;
import com.eta.modules.sys.model.SysDealLog;
import com.eta.modules.sys.model.SysUser;
import com.eta.modules.sys.service.SysLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 系统日志，切面处理类
 *
 */
@Aspect
@Component
public class SysDealLogAspect {
	@Autowired
	private SysLogService sysLogService;
	
	@Pointcut("@annotation(com.eta.core.annotation.SysLog)")
	public void logPointCut() { 
		
	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;

		//保存日志
		saveSysLog(point, time);

		return result;
	}

	private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		SysDealLog sysLog = new SysDealLog();
		SysLog syslog = method.getAnnotation(SysLog.class);
		if(syslog != null){
			//注解上的描述
			sysLog.setOperation(syslog.value());
		}

		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");

		//请求的参数
		Object[] args = joinPoint.getArgs();
		try{
			String params = new Gson().toJson(args[0]);
			sysLog.setParams(params);
		}catch (Exception e){

		}
		//获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		//设置IP地址
		sysLog.setIp(IpUtils.getIpAddr(request));

		//用户名
		//String username = ((SysDealLog) SecurityUtils.getSubject().getPrincipal()).getUsername();
		SysUser user = (SysUser)request.getSession().getAttribute("user");
		if(user!=null){
			sysLog.setUsername(user.getUsername());
		}
		sysLog.setTime(time);
		sysLog.setCreateDate(new Date());
		//保存系统日志
		sysLogService.insert(sysLog);
	}
}
