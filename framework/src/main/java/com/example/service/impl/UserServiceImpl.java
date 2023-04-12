package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.ResponseResult;
import com.example.domain.entity.User;
import com.example.domain.utils.BeanCopyUtils;
import com.example.domain.utils.SecurityUtils;
import com.example.domain.vo.UserInfoVo;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author hanxiaojian
 * @since 2023-04-10 12:23:52
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public ResponseResult userInfo() {
        // 查询数据
        User user = getById(SecurityUtils.getUserId());
        // 返回数据
        UserInfoVo userInfoVo = BeanCopyUtils.beanCopy(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }
}

