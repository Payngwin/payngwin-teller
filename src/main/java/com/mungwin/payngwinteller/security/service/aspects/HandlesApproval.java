package com.mungwin.payngwinteller.security.service.aspects;

import java.util.List;

public interface HandlesApproval {
    void doApprove(List<String> authorities);
}
