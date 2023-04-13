package com.example.job;

import com.example.domain.entity.Article;
import com.example.domain.utils.RedisCache;
import com.example.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 定时更新文章浏览量
 * @Author：hanxiaojian
 * @Date：2023/4/13 18:44
 */
@Component
public class UpdateViewCountJob {
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    /**
     * 每5秒钟执行一次定时任务，将浏览量保存在数据库中
      */
    @Scheduled(cron = "0/5 * * * * ?")
    public void updateViewCount(){
        // 获取redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");

        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        //更新到数据库中
        articleService.updateBatchById(articles);

    }
}
