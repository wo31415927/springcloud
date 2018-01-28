package com.springcloud.consumer.controller;

import com.springcloud.consumer.pojo.IProductService;
import com.springcloud.consumer.pojo.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author cctv 2018/1/27
 */
@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private IProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Product> list() {
        return this.productService.list();
    }

    @RequestMapping(value = "/{itemCode}", method = RequestMethod.GET)
    public Product detail(@PathVariable String itemCode) {
        return this.productService.detail(itemCode);
    }
}
