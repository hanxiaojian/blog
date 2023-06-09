package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.ResponseResult;
import com.example.domain.dto.AddArticleDto;
import com.example.domain.dto.GetArticleDto;
import com.example.domain.entity.Article;

/**
 * (Blog)表服务接口
 *
 * @author hanxiaojian
 * @since 2023-03-26 19:18:00
 */
public interface ArticleService extends IService<Article> {

    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult addArticle(AddArticleDto addArticleDto);


    /**
     * 根据文章标题和摘要分页查询
     * @param pageNum
     * @param pageSize
     * @param getArticleDto
     * @return
     */
    ResponseResult getArticleList(Integer pageNum, Integer pageSize, GetArticleDto getArticleDto);
}

