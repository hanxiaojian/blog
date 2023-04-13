package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.ResponseResult;
import com.example.domain.entity.User;
import com.example.domain.utils.BeanCopyUtils;
import com.example.domain.utils.SecurityUtils;
import com.example.domain.vo.UserInfoVo;
import com.example.enums.AppHttpCodeEnum;
import com.example.exception.SystemException;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 用户表(User)表服务实现类
 *
 * @author hanxiaojian
 * @since 2023-04-10 12:23:52
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
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

    @Override
    public ResponseResult register(User user) {
        // 判断数据是否为空
        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        // 判断数据是否存在
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        if(passwordExist(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(emailExist(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        // 密码加密
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        // 数据保存
        save(user);
        return ResponseResult.okResult();
    }

    public boolean userNameExist(String name) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserName, name);
        return count(lambdaQueryWrapper) > 0;
    }
    public boolean nickNameExist(String name) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getNickName, name);
        return count(lambdaQueryWrapper) > 0;
    }
    public boolean passwordExist(String name) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getPassword, name);
        return count(lambdaQueryWrapper) > 0;
    }
    public boolean emailExist(String name) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getEmail, name);
        return count(lambdaQueryWrapper) > 0;
    }
}

