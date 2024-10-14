package com.example.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.constants.SystemConstants;
import com.example.domain.ResponseResult;
import com.example.domain.dto.AddArticleDto;
import com.example.domain.entry.Article;
import com.example.domain.entry.ArticleTag;
import com.example.domain.entry.Category;
import com.example.domain.entry.Tag;
import com.example.domain.vo.*;
import com.example.mapper.ArticleMapper;
import com.example.mapper.ArticleTagMapper;
import com.example.service.ArticleService;
import com.example.service.ArticleTagService;
import com.example.service.CategoryService;
import com.example.utils.BeanCopyUtils;
import com.example.utils.RedisCache;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Resource
    private ArticleTagService articleTagService;

    @Resource
    private ArticleTagMapper articleTagMapper;


    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult hotAritcleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        queryWrapper.orderByDesc(Article::getViewCount);
        Page<Article> articlePage = new Page<>(1, 10);
        page(articlePage, queryWrapper);
        List<Article> articles = articlePage.getRecords();
        List<HotArticleVo> articleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return ResponseResult.okResult(articleVos);
    }


    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        queryWrapper.orderByDesc(Article::getIsTop);

        Page<Article> articlePage = new Page<>(pageNum, pageSize);
        page(articlePage, queryWrapper);

        List<Article> records = articlePage.getRecords();

        records.stream().map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName())).collect(Collectors.toList());

//        for (Article record : records) {
//
//            record.setCategoryName(categoryService.getById(record.getCategoryId()).getName());
//        }

        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(records, ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos,articlePage.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        Article article = getById(id);
        Integer cacheMapValue = redisCache.getCacheMapValue("Article:viewCount", id.toString());
        article.setViewCount(cacheMapValue.longValue());
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if(category != null){
            articleDetailVo.setCategoryName(category.getName());
        }
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
       redisCache.incrementCacheMapValue("Article:viewCount",id.toString(),1);
       return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult add(AddArticleDto addArticleDto) {
        Article article = BeanCopyUtils.copyBean(addArticleDto, Article.class);
        save(article);
        List<ArticleTag> articleTags = addArticleDto.getTags().stream().map(tagId->
             new ArticleTag(article.getId(),tagId)
        ).toList();
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult adminArticleList(Integer pageNum, Integer pageSize, AddArticleDto addArticleDto) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Objects.nonNull(addArticleDto.getTitle()),Article::getTitle,addArticleDto.getTitle());
        queryWrapper.like(Objects.nonNull(addArticleDto.getSummary()),Article::getSummary,addArticleDto.getSummary());
        Page<Article> articlePage = new Page<>(pageNum, pageSize);
        page(articlePage, queryWrapper);
        List<Article> articles = articlePage.getRecords();
        List<ArticleListVo2> articleListVo2s = BeanCopyUtils.copyBeanList(articles, ArticleListVo2.class);
        return ResponseResult.okResult(new PageVo(articleListVo2s,articlePage.getTotal()));
    }

    @Override
    public ResponseResult articleById(Long id) {
        Article article = getById(id);
        ArticleVo3 articleVo3 = BeanCopyUtils.copyBean(article, ArticleVo3.class);
        List<ArticleTag> tags = articleTagMapper.selectList(new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getArticleId,id));
        List<String> list = tags.stream().map(tag ->{
            return tag.getTagId().toString();
        }).toList();
        articleVo3.setTags(list);
        return ResponseResult.okResult(articleVo3);
    }

    @Override
    public ResponseResult updateArticle(ArticleVo3 articleVo3) {
        Article article = BeanCopyUtils.copyBean(articleVo3, Article.class);
        updateById(article);
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId,article.getId());
        articleTagService.remove(queryWrapper);
        List<ArticleTag> list = articleVo3.getTags().stream().map(tag -> {
            return new ArticleTag(articleVo3.getId(), Long.valueOf(tag));
        }).toList();
        articleTagService.saveBatch(list);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteById(Long id) {
        articleTagService.removeById(id);
        removeById(id);
        return ResponseResult.okResult();
    }
}
