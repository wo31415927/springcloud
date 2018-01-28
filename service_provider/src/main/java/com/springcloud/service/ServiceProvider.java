package com.springcloud.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zeyu 2018/1/26
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ServiceProvider{
    public static void main(String[] args) {
        SpringApplication.run(ServiceProvider.class, args);
    }
}
