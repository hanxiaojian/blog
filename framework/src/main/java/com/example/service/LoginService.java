package com.example.service;

import com.example.domain.ResponseResult;
import com.example.domain.entity.User;

/**
 * @Author：hanxiaojian
 * @Date：2023/4/15 15:52
 */
public interface LoginService {
    ResponseResult login(User user);

    ResponseResult loginOut();
}
