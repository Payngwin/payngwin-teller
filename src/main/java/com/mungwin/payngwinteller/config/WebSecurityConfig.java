package com.mungwin.payngwinteller.config;

import com.mungwin.payngwinteller.security.security.jwt.JwtTokenFilterConfigurer;
import com.mungwin.payngwinteller.security.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableAspectJAutoProxy
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .cors()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling().authenticationEntryPoint((req, res, ex) -> {
                    ex.printStackTrace();
                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User is unauthorized");
                })
                .and()
                .authorizeRequests()
                .antMatchers("/api/v*/public/**").permitAll()
                .antMatchers("/api/**").authenticated()
                .antMatchers("*.bundle.*").permitAll()
                .antMatchers("/**").permitAll();
        // If a user try to access a resource without having enough permissions
        http.exceptionHandling().accessDeniedHandler((req, res, ex) -> {
            ex.printStackTrace();
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User is unauthorized");
        });

        // Apply JWT
        http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/api/v*/public/**")
                .antMatchers("/#/**", "favicon.ico", "/*.js", "/*.json", "/*.css", "/*.html",
                        "/assets/**", "/static/**", "/*.woff?", "/*.tff", "/*.js.*", "/*.g.*")
                .antMatchers("/swagger-resources/**", "/swagger-ui.html", "/configuration/**")
                .antMatchers("/", "/webjars/**", "/**/*.js", "/**/*.css",
                        "/swagger-*/**", "/**/api-docs", "/csrf");
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
