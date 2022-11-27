package com.mungwin.payngwinteller.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.HashSet;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value("${spring.application.name}")
    private String appName;
    @Value("${spring.application.version}")
    private String appVersion;

    @Bean
    public Docket api() {
        HashSet<String> jsonSet = new HashSet<>(Collections.singleton("application/json"));
        return new Docket(DocumentationType.SPRING_WEB)
                .apiInfo(apiInfo())
                .consumes(jsonSet)
                .produces(jsonSet)
                .globalOperationParameters(
                        Collections.singletonList(
                                new ParameterBuilder()
                                        .name("Authorization")
                                        .description("Security token")
                                        .modelRef(new ModelRef("string"))
                                        .parameterType("header")
                                        .required(false)
                                        .build()
                        )
                );
    }
    private ApiInfo apiInfo() {
        return new ApiInfo(
                this.appName,
                "Payngwin Api",
                this.appVersion,
                "www.mungwin.com/terms",
                new Contact("Payngwin", "www.mungwin.com", "nnoukastephen@gmail.com"),
                "", "", Collections.emptyList()
        );
    }
}
