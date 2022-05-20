package com.eta.conf.config.shiro;


import com.eta.core.util.MD5Utils;
import com.eta.modules.sys.model.SysMenu;
import com.eta.modules.sys.model.SysRole;
import com.eta.modules.sys.model.SysUser;
import com.eta.modules.sys.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 */
@Component
public class MyRealm extends AuthorizingRealm {

	public MyRealm(){
		super(new AllowAllCredentialsMatcher());
        setAuthenticationTokenClass(UsernamePasswordToken.class);

        //FIXME: 暂时禁用Cache
        setCachingEnabled(false);
	}
	
	@Autowired
	private UserService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		SysUser user = (SysUser) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		SysUser dbUser = userService.findByUserName(user.getUsername());
		Set<String> shiroPermissions = new HashSet<>();
		Set<String> roleSet = new HashSet<String>();
		Set<SysRole> roles = dbUser.getRoles();
		for (SysRole role : roles) {
			Set<SysMenu> resources = role.getResources();
			if(resources==null) continue;
			for (SysMenu resource : resources) {
				if(StringUtils.isBlank(resource.getPermission())) continue;
				shiroPermissions.add(resource.getPermission());
				
			}
			roleSet.add(role.getId().toString());
		}
		authorizationInfo.setRoles(roleSet);
		authorizationInfo.setStringPermissions(shiroPermissions);
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();

		SysUser user = userService.findByUserName(username);
		
		String password = new String((char[]) token.getCredentials());

		// 账号不存在
		if (user == null) {
			throw new UnknownAccountException("用户不存在");
		}
		// 密码错误
		if (!MD5Utils.md5(password).equals(user.getPassword())) {
			throw new IncorrectCredentialsException("密码不正确");
		}
		// 账号锁定
		if (user.getStatus() == 1) {
			throw new LockedAccountException("账号已注销,请联系管理员");
		}

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());

		return info;
	}

}
