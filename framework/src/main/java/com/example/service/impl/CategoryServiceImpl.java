package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.ResponseResult;
import com.example.domain.constants.SystemConstants;
import com.example.domain.entity.Article;
import com.example.domain.entity.Category;
import com.example.domain.utils.BeanCopyUtils;
import com.example.domain.vo.CategoryVo;
import com.example.mapper.CategoryMapper;
import com.example.service.ArticleService;
import com.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.domain.constants.SystemConstants.ARTICLE_STATUS_NORMAL;
import static com.example.domain.constants.SystemConstants.STATUS_NORMAL;

/**
 * 分类表(Category)表服务实现类
 *
 * @author hanxiaojian
 * @since 2023-03-31 15:47:16
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        //查询文章表  状态为已发布的文章
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);
        //获取文章的分类id，并且去重
        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());

        //查询分类表
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream().
                filter(category -> STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.beanCopyList(categories, CategoryVo.class);


        return ResponseResult.okResult(categoryVos);
    }
}

