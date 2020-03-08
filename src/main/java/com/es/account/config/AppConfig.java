package com.es.account.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.PostConstruct;

@Configuration
public class AppConfig {

    @Autowired
    Docket docket;

    @Autowired
    BuildProperties buildProperties;

    @PostConstruct
    private void apiInfo() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title(buildProperties.getArtifact())
                .version(buildProperties.getVersion())
                .build();
        docket.apiInfo(apiInfo);
    }
}