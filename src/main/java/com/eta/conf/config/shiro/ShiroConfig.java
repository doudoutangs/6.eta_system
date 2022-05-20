package com.eta.conf.config.shiro;

import com.eta.modules.sys.model.SysMenu;
import com.eta.modules.sys.service.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Import(ShiroManager.class)
public class ShiroConfig {
	
	@Resource
	private MenuService resourceService;
	

	@Bean(name = "realm")
	@DependsOn("lifecycleBeanPostProcessor")
	@ConditionalOnMissingBean
	public Realm realm() {
		return new MyRealm();
	}
	
	 /**
     * 用户授权信息Cache
     */
    @Bean(name = "shiroCacheManager")
    @ConditionalOnMissingBean
    public CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }

    @Bean(name = "securityManager")
    @ConditionalOnMissingBean
    public DefaultSecurityManager securityManager() {
        DefaultSecurityManager sm = new DefaultWebSecurityManager();
        sm.setCacheManager(cacheManager());
        return sm;
    }

	@Bean(name = "shiroFilter")
	@DependsOn("securityManager")
	@ConditionalOnMissingBean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultSecurityManager securityManager, Realm realm) {
		securityManager.setRealm(realm);

		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setSecurityManager(securityManager);
		shiroFilter.setLoginUrl("/toLogin");
		shiroFilter.setSuccessUrl("/toIndex");
		shiroFilter.setUnauthorizedUrl("/toError");
		Map<String, String> filterChainDefinitionMap = new HashMap<String, String>();
		//filterChainDefinitionMap.put("/modules/**", "anon");
		
		//filterChainDefinitionMap.put("/admin/login", "anon");
		filterChainDefinitionMap.put("/swagger-ui.html", "anon");
		filterChainDefinitionMap.put("/webjars/**", "anon");
		filterChainDefinitionMap.put("/springfox-swagger-ui/**", "anon");
		filterChainDefinitionMap.put("/swagger-resources/**", "anon");
		filterChainDefinitionMap.put("/v2/api-docs", "anon");

		filterChainDefinitionMap.put("/api/**", "anon");

//		filterChainDefinitionMap.put("/**", "anon");

		
		List<SysMenu> list = resourceService.getAll();
		for (SysMenu resource : list) {
			if(StringUtils.isNotBlank(resource.getUrl()))
				filterChainDefinitionMap.put(resource.getUrl(), "perms[" + resource.getPermission() + "]");
		}

		//filterChainDefinitionMap.put("/**", "authc");
		shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilter;
	}
}
