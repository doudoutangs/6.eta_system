package com.eta.conf;

import com.eta.modules.sys.model.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**登录验证拦截
 * @author QQ:553039957
 * 1. gitcode主页： https://gitcode.com/user/tbb414/repos （推荐）
 * 2. github主页：https://github.com/doudoutangs
 * 
 * @Date: 2023/9/25 14:29
 */
@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {
	

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String basePath = request.getContextPath();
		String path = request.getRequestURI();
		if(!doLoginInterceptor(path, basePath) ){//是否进行登陆拦截
			return true;
		}

		if(UrlMatcher.matches(path) ){//是否进行登陆拦截
			return true;
		}

		//如果登录了，会把用户信息存进session
		HttpSession session = request.getSession();
		SysUser users =  (SysUser) session.getAttribute("user");
		if(users==null){
			String requestType = request.getHeader("X-Requested-With");
			if(requestType!=null && requestType.equals("XMLHttpRequest")){
				response.setHeader("sessionstatus","timeout");
				response.getWriter().print("LoginTimeout");
				return false;
			} else {
				log.info("尚未登录，跳转到登录界面");
				response.sendRedirect("/toLogin");
			}
			return false;
		}else{
			SysUserContext.setUser(users);
		}
		return true;
	}
	
	/**
	 * 是否进行登陆过滤
	 * @param path
	 * @param basePath
	 * @return
	 */
	private boolean doLoginInterceptor(String path,String basePath){
		path = path.substring(basePath.length());
		Set<String> notLoginPaths = new HashSet<>();
		//设置不进行登录拦截的路径：登录注册和验证码
		notLoginPaths.add("/");
		notLoginPaths.add("/toLogin");
		notLoginPaths.add("/toError");
		notLoginPaths.add("/showRegister");
		notLoginPaths.add("/sys/login/logout");

		notLoginPaths.add("/swagger-ui.html");
		notLoginPaths.add("/webjars/springfox-swagger-ui/**");
		notLoginPaths.add("/swagger-resources");

		notLoginPaths.add("/error");
		notLoginPaths.add("/main");
		notLoginPaths.add("/openapi");
		notLoginPaths.add("/static/**");

		if(notLoginPaths.contains(path)) {
			return false;
		}


		return true;
	}

	/**
	 * 暂时用正则匹配一下
	 */
	static class UrlMatcher{
		private static final String TMP_PLACEHOLDER = "@@@@@#####$$$$$";
		private static List<Pattern> includePatterns ;

		static {
			includePatterns = new ArrayList<>();
			Set<String> notLoginPaths = new HashSet<>();
			//设置不进行登录拦截的路径：登录注册和验证码
			notLoginPaths.add("/");
			notLoginPaths.add("/toLogin");
			notLoginPaths.add("/toError");
			notLoginPaths.add("/showRegister");
			notLoginPaths.add("/sys/login/logout");

			notLoginPaths.add("/swagger-ui.html");
			notLoginPaths.add("/webjars/**");
			notLoginPaths.add("/springfox-swagger-ui/**");
			notLoginPaths.add("/swagger-resources/**");
			notLoginPaths.add("/swagger-resources");
			notLoginPaths.add("/v2/api-docs/**");

			notLoginPaths.add("/error");
			notLoginPaths.add("/main");
			notLoginPaths.add("/openapi");
			notLoginPaths.add("/static/**");
			notLoginPaths.add("/api/**");


			// fixme 测试用
//			notLoginPaths.add("/**");

			for (String patternItem : notLoginPaths){
				patternItem = patternItem.trim();
				if(StringUtils.isBlank(patternItem)) {
					continue;
				}
				patternItem = patternItem.replace("**", TMP_PLACEHOLDER);
				patternItem = patternItem.replace("*", "[^/]*?");//替换*
				patternItem = patternItem.replace(TMP_PLACEHOLDER, "**");
				patternItem = patternItem.replace("**", ".*?");//替换**
				includePatterns.add(Pattern.compile(patternItem));
			}
		}


		public static boolean matches(String url)
		{
			for (Pattern pattern : includePatterns)
			{
				Matcher matcher = pattern.matcher(url);
				if(matcher.matches()) {
					return true;
				}
			}
			return false;
		}

	}







}