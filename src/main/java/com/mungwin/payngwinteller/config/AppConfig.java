package com.mungwin.payngwinteller.config;

import com.mungwin.payngwinteller.security.logs.interceptors.LogActivityContextInterceptor;
import com.mungwin.payngwinteller.security.service.auditing.AuditService;
import com.mungwin.payngwinteller.security.service.interceptors.FlushSecurityContextInterceptor;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.UUID;

@Configuration
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AppConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new FlushSecurityContextInterceptor());
        registry.addInterceptor(new LogActivityContextInterceptor());
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> factoryCustomizer() {
        return factory -> factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/"));
    }
    @Bean
    public RestTemplate restTemplate()  {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setHttpClient(httpClient);
        return new RestTemplate(httpRequestFactory);
    }
    @Bean
    AuditorAware<UUID> auditorProvider() {
        return new AuditService();
    }
}
