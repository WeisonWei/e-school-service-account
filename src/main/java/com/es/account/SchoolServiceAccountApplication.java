package com.es.account;

import org.jsondoc.spring.boot.starter.EnableJSONDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static org.springframework.data.repository.query.QueryLookupStrategy.Key.CREATE_IF_NOT_FOUND;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
@EntityScan(basePackages = {"com.es.account.entity"})
@EnableJpaRepositories(basePackages = "com.es.account.repository", queryLookupStrategy = CREATE_IF_NOT_FOUND)
@EnableSwagger2
@EnableJSONDoc
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {
        "com.es.account.service"
})
public class SchoolServiceAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolServiceAccountApplication.class, args);
    }

}
