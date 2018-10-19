package com.demo.server.service;

import com.demo.api.model.User;
import com.demo.api.service.UserService;
import org.springframework.stereotype.Service;

/**
 * Created on 2018/10/19.
 *
 * @author wangxiaodong
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public User findById(Integer id) {
        User user = new User();
        user.setId(1);
        user.setUsername("test");
        return user;
    }
}
