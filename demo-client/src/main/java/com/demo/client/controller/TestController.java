package com.demo.client.controller;

import com.demo.api.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created on 2018/10/17.
 *
 * @author wangxiaodong
 */
@RestController
public class TestController {

    @Resource
    private UserService userService;

    @GetMapping(value = "/test")
    public String test(){
        return userService.findById(1).toString();
    }
}
