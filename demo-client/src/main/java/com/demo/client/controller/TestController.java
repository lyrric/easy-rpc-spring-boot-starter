package com.demo.client.controller;

import com.easy.rpc.client.model.RpcClientProperties;
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
    private RpcClientProperties rpcClientTest;

    @GetMapping(value = "/test")
    public String test(){
        return "test";
    }
}
