package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.ResponseResult;
import com.example.domain.dto.CategoryDto;
import com.example.domain.entry.Category;
import com.example.domain.vo.CategoryVo;
import com.example.domain.vo.CategoryVo2;

import java.util.List;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2024-08-02 15:28:10
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    List<CategoryVo> listAllCategory();

    ResponseResult lists(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult add(CategoryDto categoryDto);

    ResponseResult getCategoryDetail(Long id);

    ResponseResult updateCategory(CategoryVo2 categoryDto);
}


