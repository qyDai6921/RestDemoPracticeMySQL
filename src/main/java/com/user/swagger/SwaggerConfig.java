package com.user.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.regex;

@EnableSwagger2
@Configuration
// indicates that the class has @Bean definition methods
// the class can be used by the Spring IoC container as a source of bean definitions
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.user"))
                .paths(regex("/api.*"))
                .build().apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {
        ApiInfo apiInfo = new ApiInfo("User Api", "User Api methods", "1.0", "Terms of Service Url", new Contact("Qingyang", "http://www....", "@gmail.com"), "License for User Details", "License Url", Collections.EMPTY_LIST);
        return apiInfo;
    }
}
