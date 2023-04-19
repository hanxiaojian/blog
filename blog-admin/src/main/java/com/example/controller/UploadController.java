package com.example.controller;

import com.example.domain.ResponseResult;
import com.example.enums.AppHttpCodeEnum;
import com.example.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author：hxj
 * @Date：2023/4/19 14:08
 */
@RestController
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @RequestMapping("/upload")
    public ResponseResult uploadImg(@RequestParam("img") MultipartFile multipartFile) {
        try {
            return uploadService.uploadImg(multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("文件上传上传失败");
        }
    }

}
