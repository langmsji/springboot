package com.mall.core;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;

@Component
public class LoginInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //String requestURI = httpServletRequest.getRequestURI();
        HttpSession httpSession = httpServletRequest.getSession();
        if(httpServletRequest.getRequestURI().contains("/doRegister")||httpServletRequest.getRequestURI().contains("/doLogin")||httpServletRequest.getRequestURI().contains("/error")){

        }else{
            Object isLogin = httpSession.getAttribute("currentUser");
            if(isLogin == null) {
//            httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.setContentType("application/json; charset=utf-8");
                PrintWriter out = httpServletResponse.getWriter();
                out.print("{\"success\":400,\"data\":null,\"msg\":\"用户未登录\"}");
                return false;
                //sendRedirect("/default");
            }
        }
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Origin, Authorization, Content-Type");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
