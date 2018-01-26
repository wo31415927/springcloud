package com.springcloud.consumer;

/**
 * @author zeyu 2018/1/26
 */
@EnableDiscoveryClient
@SpringBootApplication
public class Application {

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

