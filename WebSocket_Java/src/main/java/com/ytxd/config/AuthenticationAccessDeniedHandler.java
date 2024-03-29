package com.ytxd.config;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component("AuthenticationAccessDeniedHandler")
public class AuthenticationAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse resp, AccessDeniedException e) throws IOException, ServletException {
//        resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        resp.setContentType("application/json;charset=UTF-8");
//        PrintWriter out = resp.getWriter();
//        out.write("{\"status\":\"error\",\"msg\":\"权限不足，请联系管理员!\"}");
//        out.flush();
//        out.close();
        RequestDispatcher dispatcher = httpServletRequest.getRequestDispatcher("/error403");
        dispatcher.forward(httpServletRequest, resp);
    }
}