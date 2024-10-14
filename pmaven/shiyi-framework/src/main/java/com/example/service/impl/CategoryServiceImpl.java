package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.constants.SystemConstants;
import com.example.domain.ResponseResult;
import com.example.domain.dto.CategoryDto;
import com.example.domain.entry.Article;
import com.example.domain.entry.Category;
import com.example.domain.vo.CategoryVo;
import com.example.domain.vo.CategoryVo2;
import com.example.domain.vo.PageVo;
import com.example.mapper.CategoryMapper;
import com.example.service.ArticleService;
import com.example.service.CategoryService;
import com.example.utils.BeanCopyUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2024-08-02 15:28:11
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Lazy
    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<Article>();
        wrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> list = articleService.list(wrapper);
        Set<Long> collect = list.stream().map(article -> article.getCategoryId()).collect(Collectors.toSet());
        List<Category> categories = listByIds(collect);
        List<Category> collect1 = categories.stream().filter(category -> category.getStatus().equals(SystemConstants.STATUS_NORMAL)).collect(Collectors.toList());
        List<CategoryVo> vs = BeanCopyUtils.copyBeanList(collect1, CategoryVo.class);
        return ResponseResult.okResult(vs);
    }

    @Override
    public List<CategoryVo> listAllCategory() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus, SystemConstants.NORMAL);
        List<Category> list = list(wrapper);
        List<CategoryVo> vs = BeanCopyUtils.copyBeanList(list, CategoryVo.class);
        return vs;
    }

    @Override
    public ResponseResult lists(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(status),Category::getStatus, status);
        wrapper.like(Objects.nonNull(name),Category::getName, name);
        Page<Category> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);
        List<CategoryVo2> categoryVo2s = BeanCopyUtils.copyBeanList(page.getRecords(), CategoryVo2.class);
        return ResponseResult.okResult(new PageVo(categoryVo2s, page.getTotal()));
    }

    @Override
    public ResponseResult add(CategoryDto categoryDto) {
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        save(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getCategoryDetail(Long id) {
        Category category = getById(id);
        CategoryDto categoryDto = BeanCopyUtils.copyBean(category, CategoryDto.class);
        return ResponseResult.okResult(categoryDto);
    }

    @Override
    public ResponseResult updateCategory(CategoryVo2 categoryVo2) {
        Category category = BeanCopyUtils.copyBean(categoryVo2, Category.class);
        updateById(category);
        return ResponseResult.okResult();
    }


}


