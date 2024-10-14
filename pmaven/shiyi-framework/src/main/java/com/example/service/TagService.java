package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.ResponseResult;
import com.example.domain.dto.TagListDto;
import com.example.domain.entry.Tag;
import com.example.domain.vo.PageVo;
import com.example.domain.vo.TagVo;
import com.example.domain.vo.TagVo2;

import java.util.List;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2024-08-08 15:13:29
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    List<TagVo2> listAllTag();

    ResponseResult addTag(TagVo tag);

    ResponseResult deleteTag(Long id);

    ResponseResult getTagInfo(Long id);

    ResponseResult updateTag(TagVo tag);
}


