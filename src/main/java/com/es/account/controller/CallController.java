package com.es.account.controller;

import com.es.api.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class CallController {

    @Autowired
    private LoadBalancerClient loadBalancer;

    @Autowired
    SessionService sessionService;

    @RequestMapping("/call")
    public String call() {
        ServiceInstance serviceInstance = loadBalancer.choose("service-producer");
        System.out.println("服务地址：" + serviceInstance.getUri());
        System.out.println("服务名称：" + serviceInstance.getServiceId());

        String callServiceResult = new RestTemplate().getForObject(serviceInstance.getUri().toString() + "/hello", String.class);
        System.out.println(callServiceResult);
        return callServiceResult;
    }

    @PostMapping("/set/{name}")
    public String setSession(@PathVariable("name") String name) {
        String session = sessionService.setSession(name);
        return session;
    }

    @GetMapping("/get/")
    public String getSession() {
        String session = sessionService.getSession();
        return session;
    }
}
