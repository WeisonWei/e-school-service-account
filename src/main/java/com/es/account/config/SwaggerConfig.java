package com.es.account.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.PostConstruct;


@Configuration
public class SwaggerConfig {

    @Autowired
    Docket docket;

    //@Autowired
    //BuildProperties buildProperties;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("com.murphy")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.es.account.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    @PostConstruct
    private void apiInfo() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                //.title(buildProperties.getArtifact())
                //.version(buildProperties.getVersion())
                .title("school-student-service")
                .version("0.0.1-SNAPSHOT")
                .build();
        docket.apiInfo(apiInfo);
    }


}