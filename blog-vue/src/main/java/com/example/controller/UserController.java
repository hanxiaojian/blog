package com.example.controller;

import com.example.domain.ResponseResult;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.web.servlet.oauth2.resourceserver.OAuth2ResourceServerSecurityMarker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    public ResponseResult userInfo() {
        return userService.userInfo();
    }
}
