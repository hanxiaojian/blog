package com.example.controller;

import com.example.domain.ResponseResult;
import com.example.domain.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.web.servlet.oauth2.resourceserver.OAuth2ResourceServerSecurityMarker;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    public ResponseResult userInfo() {
        return userService.userInfo();
    }

    @PostMapping("/userInfo")
    public ResponseResult updateUserInfo(@RequestBody User user) {
        return userService.updateUserInfo(user);
    }
    @PostMapping("/register")
    public ResponseResult register(@RequestBody User user) {
        return userService.register(user);
    }
}
