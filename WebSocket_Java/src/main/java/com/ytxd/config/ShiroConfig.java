//package com.ytxd.config;
//import java.util.LinkedHashMap;
//
//import javax.servlet.Filter;
//
//import org.apache.shiro.mgt.SecurityManager;
//import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.ytxd.authentication.JWTFilter;
//import com.ytxd.authentication.ShiroRealm;
//
///**
// * Shiro 配置类
// *
// * @author MrBird
// */
//@Configuration("ShiroConfig")
//public class ShiroConfig {
//
//    @Bean
//    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
//        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        // 设置 securityManager
//        shiroFilterFactoryBean.setSecurityManager(securityManager);
//        shiroFilterFactoryBean.setUnauthorizedUrl("/sys/unauthorized");
//        
//        // 在 Shiro过滤器链上加入 JWTFilter
//        LinkedHashMap<String, Filter> filters = new LinkedHashMap<>();
//        filters.put("jwt", new JWTFilter());
//        shiroFilterFactoryBean.setFilters(filters);
//        //登录界面        
//        shiroFilterFactoryBean.setLoginUrl("/login");
//        //未授权界面
//        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
//
//        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//        filterChainDefinitionMap.put("/login", "anon"); //登录页面
//        filterChainDefinitionMap.put("/signin", "anon"); //用户密码登录
//        filterChainDefinitionMap.put("/unauthorized", "anon"); //未认证
//        filterChainDefinitionMap.put("/css/**", "anon");
//    	filterChainDefinitionMap.put("/js/**", "anon");
//    	filterChainDefinitionMap.put("/fonts/**", "anon");
//    	filterChainDefinitionMap.put("/img/**", "anon");
//    	filterChainDefinitionMap.put("/druid/**", "anon");
//    	filterChainDefinitionMap.put("/ResetPasswordByEmail/**", "anon");
//    	filterChainDefinitionMap.put("/logout", "logout");
//    	filterChainDefinitionMap.put("/", "anon");
//        // 所有请求都要经过 jwt过滤器
//        filterChainDefinitionMap.put("/**", "jwt");
//        
//        // 登录成功后要跳转的链接
////        shiroFilterFactoryBean.setSuccessUrl("/main.do");
//
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//        return shiroFilterFactoryBean;
//    }
//
//    @Bean
//    public SecurityManager securityManager() {
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        // 配置 SecurityManager，并注入 shiroRealm
//        securityManager.setRealm(shiroRealm());
//        return securityManager;
//    }
//
//    @Bean
//    public ShiroRealm shiroRealm() {
//        // 配置 Realm
//        return new ShiroRealm();
//    }
//
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
//        return authorizationAttributeSourceAdvisor;
//    }
//}
