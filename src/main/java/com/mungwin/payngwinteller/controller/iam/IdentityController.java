package com.mungwin.payngwinteller.controller.iam;

import com.mungwin.payngwinteller.domain.request.iam.LoginRequest;
import com.mungwin.payngwinteller.domain.response.ApiResponse;
import com.mungwin.payngwinteller.domain.response.iam.JWTResponse;
import com.mungwin.payngwinteller.security.service.AppSecurityContextHolder;
import com.mungwin.payngwinteller.service.IdentityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("${api.base}/public/iam")
public class IdentityController {
    private final IdentityService identityService;

    public IdentityController(IdentityService identityService) {
        this.identityService = identityService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JWTResponse>> login(@RequestBody @Valid LoginRequest body) {
        AppSecurityContextHolder.setFailIfAbsent(false);
        return ResponseEntity.ok(ApiResponse.from(
                identityService.login(body.getEmail(), body.getPassword(), body.getDurationInSeconds())));
    }
}
