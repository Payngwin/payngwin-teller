package com.mungwin.payngwinteller.service;

import com.mungwin.payngwinteller.domain.model.iam.App;
import com.mungwin.payngwinteller.domain.repository.iam.AppRepository;
import com.mungwin.payngwinteller.domain.repository.iam.AppTokenRepository;
import com.mungwin.payngwinteller.domain.response.iam.JWTResponse;
import com.mungwin.payngwinteller.security.contstants.PrincipalType;
import com.mungwin.payngwinteller.security.contstants.TokenScope;
import com.mungwin.payngwinteller.security.exception.ApiException;
import com.mungwin.payngwinteller.security.exception.Precondition;
import com.mungwin.payngwinteller.security.security.jwt.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Service
public class IdentityService {
    private final AppRepository appRepository;
    private final AppTokenRepository appTokenRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public IdentityService(AppRepository appRepository, AppTokenRepository appTokenRepository,
                           JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.appRepository = appRepository;
        this.appTokenRepository = appTokenRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public JWTResponse login(String email, String password) {
        Precondition.check(Arrays.stream((new String[]{email, password})).noneMatch(Objects::isNull),
                ApiException.APP_UNAUTHORIZED);
        Optional<App> optionalApp = appRepository.findFirstByEmail(email);
        Precondition.check(optionalApp.isPresent(), ApiException.APP_UNAUTHORIZED);
        App app = optionalApp.get();
        Precondition.check(passwordEncoder.matches(password, app.getPassword()), ApiException.APP_UNAUTHORIZED);
        return jwtService.encode(app.getEmail(), app.getId().toString(), PrincipalType.app, TokenScope.write);
    }
}
