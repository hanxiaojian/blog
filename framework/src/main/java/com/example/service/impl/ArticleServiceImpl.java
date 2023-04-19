package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.ResponseResult;
import com.example.domain.constants.SystemConstants;
import com.example.domain.dto.AddArticleDto;
import com.example.domain.entity.Article;
import com.example.domain.entity.ArticleTag;
import com.example.domain.entity.Category;
import com.example.domain.utils.BeanCopyUtils;
import com.example.domain.utils.RedisCache;
import com.example.domain.vo.ArticleDetailVo;
import com.example.domain.vo.ArticleListVo;
import com.example.domain.vo.HotArticleVo;
import com.example.domain.vo.PageVo;
import com.example.enums.AppHttpCodeEnum;
import com.example.mapper.ArticleMapper;
import com.example.service.ArticleService;
import com.example.service.ArticleTagService;
import com.example.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.domain.constants.SystemConstants.ARTICLE_STATUS_NORMAL;

/**
 * (Blog)表服务实现类
 *
 * @author hanxiaojian
 * @since 2023-03-26 19:18:02
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleTagService articleTagService;
    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 必须是正式文章，不是草稿
        queryWrapper.eq(Article::getStatus, ARTICLE_STATUS_NORMAL);
        // 浏览量降序排序
        queryWrapper.orderByDesc(Article::getViewCount);
        // 浏览量前10
        Page<Article> page = new Page<>(1, 10);
        page(page, queryWrapper);

        List<Article> result = page.getRecords();
//        List<HotArticleVo> hotArticleVoList = new ArrayList<>();
//        for (Article article : result) {
//            HotArticleVo hotArticleVo = new HotArticleVo();
//            // Bean的拷贝
//            BeanUtils.copyProperties(article, hotArticleVo);
//            hotArticleVoList.add(hotArticleVo);
//        }
        List<HotArticleVo> hotArticleVoList = BeanCopyUtils.beanCopyList(result, HotArticleVo.class);
        return ResponseResult.okResult(hotArticleVoList);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 如果有categoryId,则加上categoryId
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        // 文章为正式发布类型
        lambdaQueryWrapper.eq(Article::getStatus, ARTICLE_STATUS_NORMAL);
        // 置顶文章处理
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);
        // 分页处理
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, lambdaQueryWrapper);


        List<Article> articles = page.getRecords();
        for (Article article : articles) {
            Long id = article.getCategoryId();
            Category category = categoryService.getById(id);
            article.setCategoryName(category.getName());
        }
        List<ArticleListVo> articleListVos = BeanCopyUtils.beanCopyList(articles, ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        Article article = getById(id);
        // 从redis中读取文章浏览量
        Integer value = redisCache.getCacheMapValue("article:viewcount", id.toString());
        article.setViewCount(value.longValue());

        ArticleDetailVo articleDetailVo = BeanCopyUtils.beanCopy(article, ArticleDetailVo.class);
        // 通过分类ID找分类名称
        Long categoryId = article.getCategoryId();
        articleDetailVo.setCategoryName(categoryService.getById(categoryId).getName());
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        // 更新redis值
        redisCache.incrementCacheMapValue("article:viewCount", id.toString(),1);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult addArticle(AddArticleDto addArticleDto) {
        // 保存文章信息
        Article article = BeanCopyUtils.beanCopy(addArticleDto, Article.class);
        save(article);
        List<ArticleTag> collect = addArticleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());
        // 建立文章与标签的关联
        articleTagService.saveBatch(collect);
        return ResponseResult.okResult();
    }

}

