package com.springcloud.consumer.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import lombok.extern.slf4j.Slf4j;

/** @author cctv 2018/1/27 */
@Slf4j
@RestController
public class HelloController {
  @Bean
  @LoadBalanced
  RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Primary
  @Bean(name = "lbcRestTemplate")
  RestTemplate lbcRestTemplate() {
    return new RestTemplate();
  }

  @Autowired
  @Qualifier(value = "restTemplate")
  private RestTemplate restTemplate;

  @Autowired
  @Qualifier(value = "lbcRestTemplate")
  private RestTemplate lbcRestTemplate;

  @Autowired private LoadBalancerClient loadBalancerClient;

  @HystrixCommand(fallbackMethod = "helloFallback")
  @RequestMapping(value = "/hello", method = RequestMethod.GET)
  public String hello() {
    //自动负载均衡，请求的是服务名
    return restTemplate.getForEntity("http://SERVICE/hello", String.class).getBody();
  }

  @RequestMapping(value = "/helloEx", method = RequestMethod.GET)
  public String helloEx() {
    //手动负载均衡
    ServiceInstance instance = this.loadBalancerClient.choose("SERVICE");
    URI helloUri =
        //请求的是真实的地址
        URI.create(String.format("http://%s:%s/hello", instance.getHost(), instance.getPort()));
    log.info("Target service uri = {}. ", helloUri.toString());
    return this.lbcRestTemplate.getForEntity(helloUri, String.class).getBody();
  }

  public String helloFallback() {
    return "Service unavailable!";
  }
}
