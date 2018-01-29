package com.springcloud.service.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zeyu 2018/1/29
 */
@RestController
@RequestMapping("/cfg")
public class ConfigController {
    @Value("${foo}")
    String foo;

    @Value("${bar}")
    String bar;

    @RequestMapping(value = "/foo")
    public String foo(){
        return foo + "——" + bar;
    }
}

