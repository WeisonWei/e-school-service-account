package com.es.account.service;


import com.es.account.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "user-oauth2-jwt-service")
public interface UserService {

    @GetMapping("/inner/users/{name}")
    public ResponseEntity<User> getUser(@PathVariable("name") String name);
}
