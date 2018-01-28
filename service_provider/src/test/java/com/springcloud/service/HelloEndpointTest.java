package com.springcloud.service;

import com.google.common.truth.Truth;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/** @author cctv 2018/1/27 */
public class HelloEndpointTest {
  RestTemplate restTemplate = new RestTemplate();
  String url = "http://localhost:2100/";

  @BeforeClass
  public static void setUp() throws Exception {
    //    SpringApplication.run(Application.class, new String[0]);
  }

  public void tearDown() throws Exception {}

  @Test
  public void testHelloGet() throws Exception {
    ResponseEntity<String> response = restTemplate.getForEntity(url + "/hello", String.class);
    assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    System.out.println(response.getBody());
  }

  @Test
  public void testHelloPost() throws Exception {
    ResponseEntity<String> response =
        restTemplate.postForEntity(url + "/hello", new Request("Post", "8080"), String.class);
    assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    System.out.println(response.getBody());
  }

  @Test
  public void testHelloPostObject() throws Exception {
    ResponseEntity<Request> response =
        restTemplate.postForEntity(url + "/hello", new Request("Post", "8080"), Request.class);
    assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    Request request = response.getBody();
    Truth.assertThat(request.getPort()).isEqualTo("2100");
  }

  @Test
  public void testHelloExchange() throws Exception {
    ResponseEntity<Request> response =
        //更通用的方法
        restTemplate.exchange(
            url + "/hello",
            HttpMethod.POST,
            new HttpEntity<Object>(new Request("Post", "8080")),
            Request.class);
    assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    Request request = response.getBody();
    Truth.assertThat(request.getPort()).isEqualTo("2100");
  }

  @Test
  public void testOptionsForAllow() throws Exception {
    Set<HttpMethod> optionsForAllow = restTemplate.optionsForAllow(url + "/hello");
    System.out.println(optionsForAllow);
  }


  @AllArgsConstructor
  @Getter
  @Setter
  public static class Request {
    String method;
    String port;

    public String getMethod() {
      return method;
    }

    public void setMethod(String method) {
      this.method = method;
    }

    public String getPort() {
      return port;
    }

    public void setPort(String port) {
      this.port = port;
    }
  }
}
