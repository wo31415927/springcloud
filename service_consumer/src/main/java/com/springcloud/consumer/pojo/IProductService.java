package com.springcloud.consumer.pojo;

import com.springcloud.consumer.fallback.ProductServiceFallback;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/** @author cctv 2018/1/27 */
//fallback,断路器设置
@FeignClient(name="SERVICE",fallback = ProductServiceFallback.class)
public interface IProductService {

  @RequestMapping(value = "/products", method = RequestMethod.GET)
  List<Product> list();

  @RequestMapping(value = "/products/{itemCode}", method = RequestMethod.GET)
  Product detail(@PathVariable("itemCode") String itemCode);
}
