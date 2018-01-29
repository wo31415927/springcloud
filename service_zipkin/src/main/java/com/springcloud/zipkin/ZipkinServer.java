package com.springcloud.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import zipkin.server.EnableZipkinServer;

/** @author zeyu 2018/1/26 */
@EnableDiscoveryClient
@EnableZipkinServer
@SpringBootApplication
public class ZipkinServer {
  public static void main(String[] args) {
    SpringApplication.run(ZipkinServer.class, args);
  }
}
