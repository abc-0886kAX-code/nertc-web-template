//package com.ytxd.config;
//
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.ytxd.common.util.CASUtil;
//
//import javax.servlet.*;
//import javax.servlet.FilterConfig;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
//public class LocalUserInfoFilter implements Filter {
//	Logger logger = LoggerFactory.getLogger(LocalUserInfoFilter.class);
//
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//
//	}
//
//	@Override
//	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//		HttpServletRequest request = (HttpServletRequest) req;
//		String loginName = CASUtil.getAccountNameFromCas(request);
//		if (StringUtils.isNotEmpty(loginName)) {
//			logger.info("访问者 ：" + loginName);
//			request.getSession().setAttribute("loginName", loginName);
//		}
//
//		chain.doFilter(req, res);
//	}
//
//	@Override
//	public void destroy() {
//
//	}
//}