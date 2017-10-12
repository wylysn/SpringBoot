package com.purang.SpringBoot.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.purang.SpringBoot.domain.UserEntity;
import com.purang.SpringBoot.utils.MyLogger;

@Controller
public class HomeController {
    @RequestMapping({"/","/index"})
    public String index(){
        return"/index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, Map<String, Object> map) throws Exception{
//        System.out.println("HomeController.login()");
//        // 登录失败从request中获取shiro处理的异常信息。
//        // shiroLoginFailure:就是shiro异常类的全类名.
//        String exception = (String) request.getAttribute("shiroLoginFailure");
//        System.out.println("exception=" + exception);
        String msg = "";
//        if (exception != null) {
//            if (UnknownAccountException.class.getName().equals(exception)) {
//                System.out.println("UnknownAccountException -- > 账号不存在：");
//                msg = "UnknownAccountException -- > 账号不存在：";
//            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
//                System.out.println("IncorrectCredentialsException -- > 密码不正确：");
//                msg = "IncorrectCredentialsException -- > 密码不正确：";
//            } else if ("kaptchaValidateFailed".equals(exception)) {
//                System.out.println("kaptchaValidateFailed -- > 验证码错误");
//                msg = "kaptchaValidateFailed -- > 验证码错误";
//            } else {
//                msg = "else >> "+exception;
//                System.out.println("else -- >" + exception);
//            }
//        } 
        
        map.put("msg", msg);
        // 此方法不处理登录成功,由shiro进行处理
        return "/login";
    }
    
    @RequestMapping("/loginUser")
    public String loginUser(String username,String password,HttpSession session, Map<String, Object> map) {
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        String msg = "";
        try {
            subject.login(usernamePasswordToken);   //完成登录
            UserEntity user=(UserEntity) subject.getPrincipal();
            System.err.println(user);
            session.setAttribute("user", user);
            map.put("user", user);
            return "index";
        } catch(Exception e) {
        		if (e instanceof UnknownAccountException) {
                System.out.println("UnknownAccountException -- > 账号不存在：");
                msg = "UnknownAccountException -- > 账号不存在：";
            } else if (e instanceof IncorrectCredentialsException) {
                System.out.println("IncorrectCredentialsException -- > 密码不正确：");
                msg = "IncorrectCredentialsException -- > 密码不正确：";
            } else if ("kaptchaValidateFailed".equals(e.getMessage())) {
                System.out.println("kaptchaValidateFailed -- > 验证码错误");
                msg = "kaptchaValidateFailed -- > 验证码错误";
            } else {
                msg = "else >> "+e.getMessage();
                System.out.println("else -- >" + e.getMessage());
            }
        		map.put("msg", msg);
            return "login";//返回登录页面
        }
        
    }

    @RequestMapping("/error/403")
    public String unauthorizedRole(){
        System.out.println("------没有权限-------");
        return "/error/403";
    }

    @RequestMapping("/logOut")
    public String logOut(HttpServletRequest request, Map<String, Object> map) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        map.put("msg", "退出成功！");
        return "login";
    }
}