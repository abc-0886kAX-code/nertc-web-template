package com.ytxd.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ytxd.service.SystemManage.SM_Users_Service;

@Configuration("WebSecurityConfig")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	MyFilterInvocationSecurityMetadataSource myFilterInvocationSecurityMetadataSource;
	@Autowired
	MyAccessDecisionManager myAccessDecisionManager;
	@Autowired
	AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;
	@Autowired
	SM_Users_Service SM_Users_Service;

	/**
	 * 自定义的加密算法
	 * 
	 * @return
	 */
	@Bean
	public PasswordEncoder myPasswordEncoder() {
		return new MyPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(SM_Users_Service).passwordEncoder(myPasswordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/index.html", "/static/**", "/login", "/register");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
			@Override
			public <O extends FilterSecurityInterceptor> O postProcess(O o) {
				o.setSecurityMetadataSource(myFilterInvocationSecurityMetadataSource);
				o.setAccessDecisionManager(myAccessDecisionManager);
				return o;
			}
		}).and().formLogin().loginPage("/login.html").loginProcessingUrl("/login.html").usernameParameter("username")
				.passwordParameter("password").permitAll().failureHandler(new AuthenticationFailureHandler() {
					@Override
					public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
							HttpServletResponse httpServletResponse, AuthenticationException e)
							throws IOException, ServletException {
						httpServletResponse.setContentType("application/json;charset=utf-8");
						PrintWriter out = httpServletResponse.getWriter();
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("code", 401);
						if (e instanceof LockedException) {
							map.put("msg", "账户被锁定，登录失败");
						} else if (e instanceof BadCredentialsException) {
							map.put("msg", "用户名或密码输入错误，登录失败!");
						} else if (e instanceof DisabledException) {
							map.put("msg", "账户被禁用，登录失败");
						} else if (e instanceof AccountExpiredException) {
							map.put("msg", "账户过期，登录失败");
						} else if (e instanceof CredentialsExpiredException) {
							map.put("msg", "密码过期，登录失败");
						} else {
							map.put("msg", "登录失败！");
						}
                        //把map弄成json字符串写出去
                        out.write(new ObjectMapper().writeValueAsString(map));
						out.flush();
						out.close();
					}
				}).successHandler(new AuthenticationSuccessHandler() {
					@Override
					public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
							HttpServletResponse httpServletResponse, Authentication authentication)
							throws IOException, ServletException {
						httpServletResponse.setContentType("application/json;charset=utf-8");
						PrintWriter out = httpServletResponse.getWriter();
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("code", 200);
						// 获取登录成功的用户对象
						map.put("msg", authentication.getPrincipal());
						// 把map弄成json字符串写出去
						out.write(new ObjectMapper().writeValueAsString(map));
						out.flush();
						out.close();
					}
				}).and().logout().permitAll().and().csrf().disable().exceptionHandling()
				.accessDeniedHandler(authenticationAccessDeniedHandler);
	}
}