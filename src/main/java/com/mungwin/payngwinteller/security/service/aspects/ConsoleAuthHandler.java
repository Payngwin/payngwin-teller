package com.mungwin.payngwinteller.security.service.aspects;

import com.mungwin.payngwinteller.exception.ApiException;
import com.mungwin.payngwinteller.security.service.AppSecurityContextHolder;

import java.util.List;

public class ConsoleAuthHandler implements HandlesApproval{
    @Override
    public void doApprove(List<String> authorities) {
        if (AppSecurityContextHolder.getConsoleId() == null) throw ApiException.INVALID_RESOURCE_ID;
    }
}
