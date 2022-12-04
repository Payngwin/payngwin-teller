package com.mungwin.payngwinteller.service;

import com.mungwin.payngwinteller.domain.model.iam.App;
import com.mungwin.payngwinteller.domain.model.iam.AppToken;
import com.mungwin.payngwinteller.domain.model.logs.AppTokenLog;
import com.mungwin.payngwinteller.domain.repository.iam.AppRepository;
import com.mungwin.payngwinteller.domain.repository.iam.AppTokenRepository;
import com.mungwin.payngwinteller.domain.repository.logs.AppTokenLogRepository;
import com.mungwin.payngwinteller.domain.response.iam.JWTResponse;
import com.mungwin.payngwinteller.security.contstants.PrincipalType;
import com.mungwin.payngwinteller.security.contstants.TokenScope;
import com.mungwin.payngwinteller.exception.ApiException;
import com.mungwin.payngwinteller.exception.Precondition;
import com.mungwin.payngwinteller.security.logs.LogActivityContextHolder;
import com.mungwin.payngwinteller.security.service.jwt.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Service
public class IdentityService {
    private final AppRepository appRepository;
    private final AppTokenRepository appTokenRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AppTokenLogRepository appTokenLogRepository;

    public IdentityService(AppRepository appRepository, AppTokenRepository appTokenRepository,
                           JwtService jwtService, PasswordEncoder passwordEncoder,
                           AppTokenLogRepository appTokenLogRepository) {
        this.appRepository = appRepository;
        this.appTokenRepository = appTokenRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.appTokenLogRepository = appTokenLogRepository;
    }

    @Transactional
    public JWTResponse login(String email, String password, Long duration) {
        Precondition.check(Arrays.stream((new Object[]{email, password, duration})).noneMatch(Objects::isNull),
                ApiException.APP_UNAUTHORIZED);
        Optional<App> optionalApp = appRepository.findFirstByEmail(email);
        Precondition.check(optionalApp.isPresent(), ApiException.APP_UNAUTHORIZED);
        App app = optionalApp.get();
        Precondition.check(passwordEncoder.matches(password, app.getPassword()), ApiException.APP_UNAUTHORIZED);
        JWTResponse  response = jwtService.encode(
                app.getEmail(), app.getId().toString(), PrincipalType.app, TokenScope.write, duration);
        appTokenRepository.deleteAllByAppId(app.getId());
        AppToken  appToken = new AppToken();
        appToken.setAppId(app.getId());
        appToken.setToken(response.getAccessToken());
        appTokenRepository.save(appToken);
        // log activity
        AppTokenLog activityLog = LogActivityContextHolder.getActivityLog();
        activityLog.setTag("login");
        activityLog.setAppId(appToken.getAppId());
        activityLog.setTokenId(appToken.getId());
        activityLog.setCreatedAt(Instant.now());
        activityLog.setUpdatedAt(Instant.now());
        activityLog.setTokenCreatedAt(appToken.getCreatedAt());
        appTokenLogRepository.save(activityLog);
        return response;
    }
}
