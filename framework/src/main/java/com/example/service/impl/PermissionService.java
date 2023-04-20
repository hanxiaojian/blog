package com.example.service.impl;

import com.example.domain.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 自定义ps注解，用于判断用户是否具有权限
 * @Author：hxj
 * @Date：2023/4/20 16:36
 */
@Service("ps")
public class PermissionService {
    public Boolean hasPermissions(String permission) {
        if (SecurityUtils.isAdmin()) {
            return true;
        }
        List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
        return permissions.contains(permission);
    }
}
