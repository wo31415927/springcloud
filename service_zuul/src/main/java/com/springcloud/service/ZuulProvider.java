package com.springcloud.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author cctv 2018/1/28
 */
//@EnableZuulProxy包含了@EnableZuulServer，@EnableCircuitBreaker和@EnableDiscoveryClient功能
@EnableZuulProxy
@SpringBootApplication
public class ZuulProvider {
    public static void main(String[] args) {
        SpringApplication.run(ZuulProvider.class, args);
    }
}
