package com.es.account.config;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class AspectjTransactionConfig {

    public static final String transactionExecution = "execution (* com.es..service.*.*(..))";

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor() {
        //指定一般要拦截哪些类
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(transactionExecution);
        //配置advisor
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        //指定不同的方法用不通的策略
        Properties attributes = new Properties();
        attributes.setProperty("get*", "PROPAGATION_REQUIRED,-Exception");
        attributes.setProperty("find*", "PROPAGATION_REQUIRED,-Exception");
        attributes.setProperty("add*", "PROPAGATION_REQUIRED,-Exception");
        attributes.setProperty("save*", "PROPAGATION_REQUIRED,-Exception");
        attributes.setProperty("update*", "PROPAGATION_REQUIRED,-Exception");
        attributes.setProperty("delete*", "PROPAGATION_REQUIRED,-Exception");
        //创建Interceptor
        TransactionInterceptor txAdvice = new TransactionInterceptor(transactionManager, attributes);
        advisor.setAdvice(txAdvice);
        return advisor;
    }
}
