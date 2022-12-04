package com.mungwin.payngwinteller.security.service.interceptors;

import com.mungwin.payngwinteller.security.service.AppSecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FlushSecurityContextInterceptor implements HandlerInterceptor {
    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler, Exception ex) throws Exception {
        AppSecurityContextHolder.clearPrincipal();
    }
}
