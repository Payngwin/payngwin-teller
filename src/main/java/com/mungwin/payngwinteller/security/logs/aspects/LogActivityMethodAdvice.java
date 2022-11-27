package com.mungwin.payngwinteller.security.logs.aspects;


import com.mungwin.payngwinteller.domain.model.iam.AppToken;
import com.mungwin.payngwinteller.domain.model.logs.AppTokenLog;
import com.mungwin.payngwinteller.domain.repository.logs.AppTokenLogRepository;
import com.mungwin.payngwinteller.security.logs.LogActivityContextHolder;
import com.mungwin.payngwinteller.security.security.AppSecurityContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogActivityMethodAdvice {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private AppTokenLogRepository activityLogRepository;

    @Autowired
    public void setActivityLogRepository(AppTokenLogRepository activityLogRepository) {
        this.activityLogRepository = activityLogRepository;
    }

    @Before("@annotation(LogActivity)")
    public void approveMethod(JoinPoint joinPoint) throws Throwable {
        LogActivity logActivity = ((MethodSignature) joinPoint.getSignature())
                .getMethod().getAnnotation(LogActivity.class);
        String tag = logActivity.value();
        AppTokenLog activityLog = LogActivityContextHolder.getActivityLog();
        AppToken token = AppSecurityContextHolder.getToken();
        activityLog.setTag(tag);
        activityLog.setAppId(token.getAppId());
        activityLog.setTokenId(token.getId());
        activityLogRepository.save(activityLog);
        logger.info("saved activity log: {} for user {}", activityLog.getId(), activityLog.getAppId());
    }

}
