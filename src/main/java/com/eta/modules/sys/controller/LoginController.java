
package com.eta.modules.sys.controller;

import com.eta.core.annotation.SysLog;
import com.eta.core.entity.ProcessResult;
import com.eta.core.util.MD5Utils;
import com.eta.modules.sys.model.SysUser;
import com.eta.modules.sys.service.RoleService;
import com.eta.modules.sys.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/sys/login")
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @SysLog("用户登录")
    @PostMapping("/signIn")
    public ProcessResult login(SysUser user, HttpServletRequest request) {
        ProcessResult result = new ProcessResult();
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
            subject.login(token);
            request.getSession().setAttribute("user", userService.findByUserName(user.getUsername()));
            result.setResultStat(ProcessResult.SUCCESS);
            result.setData(request.getUserPrincipal());
            return result;
        } catch (AuthenticationException e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage());
        }
    }


    @SysLog("用户登出")
    @GetMapping("/logout")
    public ProcessResult logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new ProcessResult();
    }


    @SysLog("修改密码")
    @PostMapping("/modifyPWD")
    public ProcessResult modifyPWD(String oldpwd, String password, HttpSession session) {
        SysUser user = (SysUser) session.getAttribute("user");
        if (!MD5Utils.md5(oldpwd).equals(user.getPassword())) {
            return new ProcessResult(ProcessResult.ERROR, "原密码不正确");
        }
        user.setPassword(MD5Utils.md5(password));
        ProcessResult result = userService.save(user);
        SysUser sysUser = userService.findByUserName(user.getUsername());
        session.setAttribute("user", sysUser);
        return result;
    }

//    @PostMapping("/showVerify")
//    public  ProcessResult showVerify(String email){
//        return  userService.showVerify(email);
//    }


    @SysLog("用户注册")
    @PostMapping("/register")
    public ProcessResult register(SysUser vo) {
        return userService.saveUser(vo);
    }
}
