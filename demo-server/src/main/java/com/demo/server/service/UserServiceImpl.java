package com.demo.server.service;

import com.demo.api.model.User;
import com.demo.api.service.UserService;
import com.github.easy.rpc.server.annotation.RpcService;
import org.springframework.stereotype.Service;

/**
 * Created on 2018/10/19.
 *
 * @author wangxiaodong
 */
@Service
@RpcService
public class UserServiceImpl implements UserService {

    @Override
    public User findById(Integer id) {
        User user = new User();
        user.setId(1);
        user.setUsername("test");
        return user;
    }
}
