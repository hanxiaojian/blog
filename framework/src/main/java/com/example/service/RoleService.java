package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.ResponseResult;
import com.example.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author hanxiaojian
 * @since 2023-04-15 17:51:57
 */
public interface RoleService extends IService<Role> {
    List<String> selectRoleKeyByUserId(Long id);
}

