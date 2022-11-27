package com.mungwin.payngwinteller.security.logs;


import com.mungwin.payngwinteller.domain.model.logs.AppTokenLog;

public class LogActivityContextHolder {
    private static final ThreadLocal<AppTokenLog> activityLog = new ThreadLocal<>();
    public static AppTokenLog getActivityLog() {
        return activityLog.get();
    }
    public static void setActivityLog(AppTokenLog log) {
        LogActivityContextHolder.activityLog.set(log);
    }
    public static void flushActivityLog() {
        LogActivityContextHolder.activityLog.remove();
    }
}
