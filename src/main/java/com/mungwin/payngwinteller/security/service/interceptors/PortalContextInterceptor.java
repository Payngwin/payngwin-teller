package com.mungwin.payngwinteller.security.service.interceptors;

import com.mungwin.payngwinteller.security.Util;
import com.mungwin.payngwinteller.security.props.ResourceServerProps;
import com.mungwin.payngwinteller.security.service.AppSecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class PortalContextInterceptor implements HandlerInterceptor {
    private final ResourceServerProps resourceServerProps;

    public PortalContextInterceptor(ResourceServerProps resourceServerProps) {
        this.resourceServerProps = resourceServerProps;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String basicToken = request.getHeader("Authorization");
        Util.BasicAuthParts basicAuthParts = Util.parseBasicAuth(basicToken);
        if (basicAuthParts != null && resourceServerProps.getConsoleId().equals(basicAuthParts.getPassword())) {
            try {
                UUID.fromString(basicAuthParts.getUsername()); // username must be uuid
                AppSecurityContextHolder.setConsoleId(basicAuthParts.getUsername());
            } catch (Exception ex) {
                AppSecurityContextHolder.setConsoleId(null);
            }
        }
        return true;
    }
}
