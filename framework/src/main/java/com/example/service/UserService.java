package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.ResponseResult;
import com.example.domain.entity.User;


/**
 * 用户表(User)表服务接口
 *
 * @author hanxiaojian
 * @since 2023-04-10 11:56:29
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);
}

