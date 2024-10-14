package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.ResponseResult;
import com.example.domain.dto.AddArticleDto;
import com.example.domain.entry.Article;
import com.example.domain.vo.ArticleVo3;

public interface ArticleService extends IService<Article> {

    ResponseResult hotAritcleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto addArticleDto);

    ResponseResult adminArticleList(Integer pageNum, Integer pageSize, AddArticleDto addArticleDto);

    ResponseResult articleById(Long id);

    ResponseResult updateArticle(ArticleVo3 articleVo3);

    ResponseResult deleteById(Long id);
}
