package org.example.runner;

import com.example.domain.entry.Article;
import com.example.mapper.ArticleMapper;
import com.example.service.ArticleService;
import com.example.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        List<Article> articles = articleMapper.selectList(null);
//        for (Article article : articles) {
//            System.out.println(article.getViewCount().intValue());
//        }
        Map<String, Integer> collect = articles.stream().collect(Collectors.toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));
        redisCache.setCacheMap("Article:viewCount",collect);
    }
}
