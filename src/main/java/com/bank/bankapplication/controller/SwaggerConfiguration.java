package com.bank.bankapplication.controller;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    private static final String TITLE = "Bank Application";
    private static final String DESCRIPTION = "Bank Application";
    private static final String VERSION = "1.0";

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("banking")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI bankServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title(TITLE)
                        .description(DESCRIPTION)
                        .version(VERSION)
                        .license(new License().name("Apache 2.0")));
    }
}