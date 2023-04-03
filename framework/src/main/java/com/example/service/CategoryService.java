package com.example.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.ResponseResult;
import com.example.domain.entity.Category;

/**
 * 分类表(Category)表服务接口
 *
 * @author hanxiaojian
 * @since 2023-03-31 15:47:16
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}

