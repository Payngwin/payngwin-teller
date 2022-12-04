package com.mungwin.payngwinteller.security.service.aspects;

import java.util.List;

public class CustomApproveHandler implements HandlesApproval{
    @Override
    public void doApprove(List<String> authorities) {
        System.out.println("in custom approve handler");
    }
}
