package com.fan.shirodemo.controller;

import com.fan.shirodemo.entity.Role;
import com.fan.shirodemo.entity.User;
import com.fan.shirodemo.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName LoginController
 * @Description TODO
 * @Author Administrator
 * @Date 2018/11/9 13:59
 */

@RestController
@RequestMapping("/user")
public class LoginController {
    @Autowired
    private LoginService loginService;

    /**
     * 用于退出
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){

        return "login1";
    }

    /**
     * post登录
     * @param
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password){
           //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        subject.login(token);

        return "login2";
    }

    @RequestMapping(value = "/index")
    public String index(){

        return "index";
    }

    //登出
    @RequestMapping(value = "/logout")
    public String logout(){

        return "logout";
    }

    //错误页面展示
    @RequestMapping(value = "/error")
    public String error(){

        return "error ok!";
    }

    @RequestMapping(value = "/addUser")
    public String addUser(@RequestParam("userName")String  userName,
                          @RequestParam("password") String password){
        User user = loginService.addUser(userName,password);

        return "addRole is ok!";
    }

    @RequestMapping(value = "/addRole")
    public String addRole(@RequestParam("userId") Integer userId,
                          @RequestParam("roleName") String roleName){
        Role role = loginService.addRole(userId,roleName);

        return "addRole is ok!";
    }

    @RequiresRoles("admin")
    @RequiresPermissions("create")
    @RequestMapping("/create")
    public String create(){

        return "create sucess";
    }
}
