package com.eta.modules.sys.controller;

import com.eta.modules.sys.model.SysUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RequestMapping
@Controller
public class InitController {

    @GetMapping("/toIndex")
    public ModelAndView toIndex(ModelMap map, HttpServletRequest request) {
        SysUser vo = (SysUser) request.getSession().getAttribute("user");
        if (vo != null) {
            map.put("user", vo);
        }
        return new ModelAndView("index");
    }

    @GetMapping("/modifyPassword")
    public ModelAndView modifyPassword(ModelMap map,HttpServletRequest request) {
        SysUser vo = (SysUser) request.getSession().getAttribute("user");
        if (vo != null) {
            map.put("user", vo);
        }
        return new ModelAndView("/modules/sys/set/password");
    }

    @GetMapping("/toError")
    public String error() {
        return "error";
    }

    @GetMapping("/showRegister")
    public String showRegister() {
        return "register";
    }

    @GetMapping("/main")
    public String main() {
        return "main";
    }

    @GetMapping("/avatar")
    public String avatar() {
        return "avatar";
    }

    @RequestMapping({"/", "", "/toLogin"})
    public String home() {
        return "login";
    }
}
