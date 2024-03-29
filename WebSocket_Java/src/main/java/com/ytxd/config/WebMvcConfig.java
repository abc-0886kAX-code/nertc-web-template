package com.ytxd.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ytxd.interceptor.AuthorizationInterceptor;
//import com.ytxd.interceptor.LoginUserHandlerMethodArgumentResolver;
//import com.ytxd.interceptor.RefererInterceptor;

/**
 * MVC配置
 */
@Configuration("WebMvcConfig")
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;
//    @Autowired
//    private RefererInterceptor refererInterceptor;
//    @Autowired
//    private LoginUserHandlerMethodArgumentResolver loginUserHandlerMethodArgumentResolver;

    /**
     * @author ytxd
     * @Description 拦截器 拦截所有请求验证合法
     * @Param [registry]
     * @return void
     **/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/**").excludePathPatterns("/swagger-resources/**", "/resources/**","/webjars/**", "/v3/**", "/swagger-ui/**","/login/**");
        //registry.addInterceptor(refererInterceptor).addPathPatterns("/**");
    }

//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//        argumentResolvers.add(loginUserHandlerMethodArgumentResolver);
//    }

    /**
     * 跨域支持
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        .allowedOrigins("*")
        .allowedHeaders("*")
        .allowCredentials(true)//设置是否允许跨域传cookie
        .allowedMethods("GET", "POST", "OPTIONS", "DELETE", "PUT")
        .maxAge(3600 * 24);//设置缓存时间，减少重复响应
    }
    
    /**
     * 设置首页(默认页)跳转 
     * @param registry
     */
    @Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("/login");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}
}