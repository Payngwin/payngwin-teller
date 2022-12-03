package com.mungwin.payngwinteller.security.security.aspects;

import java.util.List;

public interface HandlesApproval {
    void doApprove(List<String> authorities);
}
