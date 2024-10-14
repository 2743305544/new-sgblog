package org.example.job;


import com.example.domain.entry.Article;
import com.example.service.ArticleService;
import com.example.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UpdateViewCountJob {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0 0/10 * * * ? ")
    public void updateViewCount() {
        Map<String, Integer> cacheMap = redisCache.getCacheMap("Article:viewCount");
        List<Article> collect = cacheMap.entrySet().stream().map(entry -> {
            return new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue());
        }).collect(Collectors.toList());
        articleService.updateBatchById(collect);
    }
}
