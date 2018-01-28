package com.springcloud.consumer.fallback;

import com.springcloud.consumer.pojo.IProductService;
import com.springcloud.consumer.pojo.Product;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author cctv 2018/1/27
 */
@Component
public class ProductServiceFallback implements IProductService {
    @Override
    public List<Product> list() {
        return Collections.emptyList();
    }

    @Override
    public Product detail(String itemCode) {
        return new Product("error", "未知", "Fallback", 0);
    }
}
