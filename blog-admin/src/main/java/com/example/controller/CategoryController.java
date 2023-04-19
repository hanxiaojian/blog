package com.example.controller;


import com.example.domain.ResponseResult;
import com.example.domain.vo.CategoryVo;
import com.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author：hxj
 * @Date：2023/4/19 10:21
 */
@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @RequestMapping("/listAllCategory")
    public ResponseResult listAllCategory() {
        List<CategoryVo> list = categoryService.listCategory();
        return ResponseResult.okResult(list);
    }
}
