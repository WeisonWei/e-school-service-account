package com.es.account.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiKey;

@Configuration
public class AppConfig {

    private ApiKey apiKey() {
        return new ApiKey("apiKey", "Authorization", "header");
    }

}