package com.demo.client.controller;

import com.demo.api.model.User;
import com.demo.api.service.UserService;
import com.easy.rpc.client.model.RpcClientProperties;
import org.springframework.beans.factory.annotation.Autowired;
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
    private RpcClientProperties rpcClientProperties;

    @Resource
    private UserService userService;

    public TestController() {
        System.out.println("TestController init");
    }

    @GetMapping(value = "/test")
    public String test(){
        return "test";
    }
}
