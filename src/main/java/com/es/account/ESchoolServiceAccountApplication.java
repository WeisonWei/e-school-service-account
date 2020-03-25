package com.es.account;

import com.es.account.config.MessageSource;
import org.jsondoc.spring.boot.starter.EnableJSONDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static org.springframework.data.repository.query.QueryLookupStrategy.Key.CREATE_IF_NOT_FOUND;

@SpringBootApplication
@EnableAsync
@EnableCaching
@EnableJSONDoc
@EnableSwagger2
@EnableScheduling
@EnableJpaAuditing
@EnableDiscoveryClient
@EnableBinding({Sink.class, Source.class, MessageSource.class})
@EntityScan(value = {"com.es.account.entity"})
@EnableFeignClients(basePackages = {"com.es.api.service"})
@EnableJpaRepositories(basePackages = "com.es.account.repository", queryLookupStrategy = CREATE_IF_NOT_FOUND)
public class ESchoolServiceAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(ESchoolServiceAccountApplication.class, args);
    }

}
