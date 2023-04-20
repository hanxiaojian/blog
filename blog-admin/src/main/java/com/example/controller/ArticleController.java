package com.example.controller;

import com.example.domain.ResponseResult;
import com.example.domain.dto.AddArticleDto;
import com.example.domain.dto.GetArticleDto;
import com.example.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：hxj
 * @Date：2023/4/19 15:02
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    public ResponseResult addArticle(@RequestBody AddArticleDto addArticleDto) {
        return articleService.addArticle(addArticleDto);
    }

    @GetMapping("/list")
    public ResponseResult getArticleList(Integer pageNum, Integer pageSize, GetArticleDto getArticleDto) {
        return articleService.getArticleList(pageNum, pageSize, getArticleDto);
    }
}
