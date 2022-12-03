package com.mungwin.payngwinteller.security.security.jwt;

import com.mungwin.payngwinteller.domain.model.iam.AppToken;
import com.mungwin.payngwinteller.domain.repository.iam.AppTokenRepository;
import com.mungwin.payngwinteller.exception.ApiException;
import com.mungwin.payngwinteller.exception.Precondition;
import com.mungwin.payngwinteller.security.props.ResourceServerProps;
import com.mungwin.payngwinteller.security.security.AppDetailsService;
import com.mungwin.payngwinteller.security.security.AppSecurityContextHolder;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class JwtTokenProvider {

  /**
   * THIS IS NOT A SECURE PRACTICE! For simplicity, we are storing a static key here. Ideally, in a
   * microservices environment, this key would be kept on a config-server.
   */
  @Autowired
  private ResourceServerProps resourceServerProps;

  @Autowired
  private AppDetailsService appDetailsService;
  @Autowired
  private JwtService jwtService;
  @Autowired
  private AppTokenRepository tokenRepository;


  public Authentication getAuthentication(String token) {
    UserDetails userDetails = appDetailsService.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getUsername(String token) {
    JwtService.OAuthResponse auth = jwtService.decode(token);
    Optional<AppToken> optionalAppToken = tokenRepository.findFirstByToken(token);
    Precondition.check(optionalAppToken.isPresent(), ApiException.INVALID_TOKEN);
    AppSecurityContextHolder.setToken(optionalAppToken.get());
    return auth.getEmail();
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring("Bearer ".length());
    }
    return null;
  }

  public boolean validateToken(String token) {
    try {
      jwtService.decode(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      throw ApiException.INVALID_TOKEN;
    }
  }

}
