package com.fan.shirodemo.shiro;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName 过滤配置
 * @Description TODO
 * @Author Administrator
 * @Date 2018/11/9 13:33
 */


@Configuration
public class ShiroConfiguration {

    /**
     * 将自己的验证方式加入容器
     * @return
     */
    @Bean
    public ShiroRealm shiroRealm(){
       ShiroRealm shiroRealm = new ShiroRealm();
       return shiroRealm;
    }

    /**
     *权限管理配置主要的realm管理认证
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());

        return securityManager;
    }

    /**
     * filter工厂,设置对应的过滤条件和跳转条件
      * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);

        Map<String,String> map = new HashMap<>();

        //登出
        map.put("/logout","logout");

        //对所有用户认证
        map.put("/**","authc");

        //登录
        filterFactoryBean.setLoginUrl("/login");

        //首页
        filterFactoryBean.setSuccessUrl("/index");

//        filterFactoryBean.setFilterChainDefinitions("/swagger-ui.html");
//        filterFactoryBean.setFilterChainDefinitions("/login");

        //错误页面,认证不通过跳转
        filterFactoryBean.setUnauthorizedUrl("/error");

        LinkedHashMap<String,String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("/templete/swagger-ui.html*","anon");
        linkedHashMap.put("/login*","anon");
        filterFactoryBean.setFilterChainDefinitionMap(linkedHashMap);

        return filterFactoryBean;
    }

    //加入注解的使用,不加入这个注解不生效
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(WebSecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
