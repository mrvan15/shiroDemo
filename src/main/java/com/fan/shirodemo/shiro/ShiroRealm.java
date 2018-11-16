package com.fan.shirodemo.shiro;

import com.fan.shirodemo.entity.Permission;
import com.fan.shirodemo.entity.Role;
import com.fan.shirodemo.entity.User;
import com.fan.shirodemo.repository.UserRepository;
import com.fan.shirodemo.service.LoginService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName ShiroRealm
 * @Description TODO
 * @Author Administrator
 * @Date 2018/11/9 11:34
 */
public class ShiroRealm  extends AuthorizingRealm {

    @Autowired
    private LoginService loginService;

    //角色权限和权限添加
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户名
        String name = (String) principalCollection.getPrimaryPrincipal();
        //查询用户名称
        User user = loginService.findByName(name);
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        for (Role role: user.getRoles()) {
            //增加角色
            simpleAuthorizationInfo.addRole(role.getRoleName());
            for (Permission permission: role.getPermissions()) {
               //添加权限
                simpleAuthorizationInfo.addStringPermission(permission.getPermission());
            }

        }
        return simpleAuthorizationInfo;
    }

    /**
     * 用户认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return  null;
        }

        String name = authenticationToken.getPrincipal().toString();
        User user = loginService.findByName(name);
        if (user == null) {
            return  null;
        }else {
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name,user.getPassword(),getName());

            return simpleAuthenticationInfo;
        }

    }
}
