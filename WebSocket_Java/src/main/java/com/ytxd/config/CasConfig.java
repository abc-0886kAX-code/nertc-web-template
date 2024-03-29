//package com.ytxd.config;
//
//import org.jasig.cas.client.authentication.AuthenticationFilter;
//import org.jasig.cas.client.session.SingleSignOutFilter;
//import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
//import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
//import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.Ordered;
//
//@Configuration("CasConfig")
//public class CasConfig {
//	@Value("${cas.server-url}")
//	private String CAS_URL;
//	@Value("${cas.client-host}")
//	private String APP_URL;
//
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Bean
//	public ServletListenerRegistrationBean servletListenerRegistrationBean() {
//		ServletListenerRegistrationBean listenerRegistrationBean = new ServletListenerRegistrationBean();
//		listenerRegistrationBean.setListener(new SingleSignOutHttpSessionListener());
//		listenerRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
//		return listenerRegistrationBean;
//	}
//
//	/**
//	 * 单点登录退出
//	 * 
//	 * @return
//	 */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Bean
//	public FilterRegistrationBean singleSignOutFilter() {
//		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//		registrationBean.setFilter(new SingleSignOutFilter());
//		registrationBean.addUrlPatterns("/*");
//		registrationBean.addInitParameter("casServerUrlPrefix", CAS_URL);
//		registrationBean.setName("CAS Single Sign Out Filter");
//		registrationBean.setOrder(2);
//		return registrationBean;
//	}
//
//	/**
//	 * 单点登录认证
//	 * 
//	 * @return
//	 */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Bean
//	public FilterRegistrationBean AuthenticationFilter() {
//		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//		registrationBean.setFilter(new AuthenticationFilter());
//		//目前只拦截对应的登录页面，可将系统默认首页和登录信息丢失或者报错等跳转到的登录面面改成这个登录页面。这样就可以保留我们原来的登录页面正常使用，如果不保留可以设置成/*
//		registrationBean.addUrlPatterns("/jxxdxysso.html");
//		registrationBean.addUrlPatterns("/jxxdxysso.htm");
//		registrationBean.addUrlPatterns("/jxxdxysso");
//		registrationBean.setName("CAS Filter");
//		registrationBean.addInitParameter("casServerLoginUrl", CAS_URL);
//		registrationBean.addInitParameter("serverName", APP_URL);
//		registrationBean.setOrder(3);
//		return registrationBean;
//	}
//
//	/**
//	 * 单点登录校验
//	 * 
//	 * @return
//	 */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Bean
//	public FilterRegistrationBean cas20ProxyReceivingTicketValidationFilter() {
//		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//		registrationBean.setFilter(new Cas20ProxyReceivingTicketValidationFilter());
//		registrationBean.addUrlPatterns("/*");
//		registrationBean.setName("CAS Validation Filter");
//		registrationBean.addInitParameter("casServerUrlPrefix", CAS_URL);
//		registrationBean.addInitParameter("serverName", APP_URL);
//		registrationBean.setOrder(4);
//		return registrationBean;
//	}
//
//	/**
//	 * 单点登录请求包装
//	 * 
//	 * @return
//	 */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Bean
//	public FilterRegistrationBean httpServletRequestWrapperFilter() {
//		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//		registrationBean.setFilter(new HttpServletRequestWrapperFilter());
//		registrationBean.addUrlPatterns("/*");
//		registrationBean.setName("CAS HttpServletRequest Wrapper Filter");
//		registrationBean.setOrder(5);
//		return registrationBean;
//	}
//
////	/**
////	 * 单点登录本地用户信息
////	 * 
////	 * @return
////	 */
////	@Bean
////	public FilterRegistrationBean localUserInfoFilter() {
////		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
////		registrationBean.setFilter(new LocalUserInfoFilter());
////		registrationBean.addUrlPatterns("/*");
////		registrationBean.setName("localUserInfoFilter");
////		registrationBean.setOrder(6);
////		return registrationBean;
////	}
//
//}
