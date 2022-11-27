package com.mungwin.payngwinteller.security.logs.interceptors;

import com.mungwin.payngwinteller.domain.model.logs.AppTokenLog;
import com.mungwin.payngwinteller.security.logs.LogActivityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogActivityContextInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AppTokenLog log = new AppTokenLog();
        log.setEndPoint(request.getRequestURI());
        log.setIpAddress(request.getRemoteAddr());
        log.setUserAgent(request.getHeader("User-Agent"));
        log.setMethod(request.getMethod());
        log.setQueryString(request.getQueryString());
        LogActivityContextHolder.setActivityLog(log);
        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler, Exception ex) throws Exception {
        LogActivityContextHolder.flushActivityLog();
    }
}
