package com.mungwin.payngwinteller.security.security.jwt;

import com.mungwin.payngwinteller.domain.response.iam.JWTResponse;
import com.mungwin.payngwinteller.exception.ApiException;
import com.mungwin.payngwinteller.security.props.ResourceServerProps;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    private ResourceServerProps resourceServerProps;


    @Autowired
    public void setResourceServerProps(ResourceServerProps resourceServerProps) {
        this.resourceServerProps = resourceServerProps;
    }

    public OAuthResponse decode(String token) {
        JSONObject payload = decode(token, resourceServerProps.getSigningKey(), resourceServerProps.getResourceId());
        return new OAuthResponse()
                .setEmail(payload.has("sub") ? payload.getString("sub") : null)
                .setPrincipal(payload.has("principal") ? payload.getString("principal") : null)
                .setScopes(payload.has("scope") ? payload.getString("scope").split(",") : null)
                .setRefresh(payload.has("is_refresh") && payload.getBoolean("is_refresh"))
                .setPrincipalType(payload.has("principal_type") ? payload.getString("principal_type") : null);
    }
    public JSONObject decode(String token, String signature, String aud){
        String[]  chunks = token.split("\\.");
        if (chunks.length < 3) throw ApiException.INVALID_TOKEN;
        Base64.Decoder decoder = Base64.getUrlDecoder();
        JSONObject header = new JSONObject(new String(decoder.decode(chunks[0])));
        JSONObject payload = new JSONObject(new String(decoder.decode(chunks[1])));
        if (isExpired(payload)) {
            throw ApiException.EXPIRED_TOKEN;
        }
        if (payload.has("aud") && !payload.getString("aud").equals(aud)) {
            throw ApiException.INVALID_RESOURCE_ID;
        }
        // verify
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.forName(header.getString("alg"));
        SecretKeySpec secretKeySpec = new SecretKeySpec(signature.getBytes(),
                signatureAlgorithm.getJcaName());
        DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(signatureAlgorithm, secretKeySpec);
        if (!validator.isValid(chunks[0] + "." + chunks[1], chunks[2])) {
            throw ApiException.INVALID_TOKEN;
        }
        return payload;
    }
    public String encode(Map<String, Object> claimsMap, String signature) {
        return Jwts.builder().addClaims(claimsMap).signWith(SignatureAlgorithm.HS256, signature.getBytes()).compact();
    }
    public JWTResponse encode(String userName, String principal, String  principalType, String[] scope, Long duration) {
        JwtBuilder jwtBuilder = Jwts.builder().claim("principal", principal)
                .claim("principal_type", principalType)
                .claim("scope", String.join(",", scope))
                .setSubject(userName)
                .setAudience(resourceServerProps.getResourceId());

        JWTResponse response = new JWTResponse();
        if (duration > -1) {
            jwtBuilder.setExpiration(Date.from(Instant.now().plusSeconds(duration)));
            response.setExpiresInSeconds(duration);
        }
        String jwt = jwtBuilder
                .signWith(SignatureAlgorithm.HS256, resourceServerProps.getSigningKey().getBytes())
                .compact();
        response.setAccessToken(jwt);
        response.setTokenType("bearer");
        return response;
    }
    public boolean isExpired(JSONObject payload) {
        try {
            Instant exp = Instant.ofEpochSecond(payload.getLong("exp"));
            return exp.isBefore(Instant.now());
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }
    public static class OAuthResponse {
        private String principal;
        private String email;
        private String principalType;
        private String[] scopes;
        private boolean refresh;

        public String getPrincipal() {
            return principal;
        }

        public OAuthResponse setPrincipal(String principal) {
            this.principal = principal;
            return this;
        }

        public String getEmail() {
            return email;
        }

        public OAuthResponse setEmail(String email) {
            this.email = email;
            return this;
        }

        public String getPrincipalType() {
            return principalType;
        }

        public OAuthResponse setPrincipalType(String principalType) {
            this.principalType = principalType;
            return this;
        }

        public String[] getScopes() {
            return scopes;
        }

        public OAuthResponse setScopes(String[] scopes) {
            this.scopes = scopes;
            return this;
        }

        public boolean isRefresh() {
            return refresh;
        }

        public OAuthResponse setRefresh(boolean refresh) {
            this.refresh = refresh;
            return this;
        }
    }

}
