package com.jenkins_demo.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/api")
public class DemoController {

    @GetMapping("/hello")
    public Mono<String> hello(){
        return Mono.just("hello");
    }
}
