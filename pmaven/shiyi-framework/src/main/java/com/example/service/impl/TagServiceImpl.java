package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.ResponseResult;
import com.example.domain.dto.TagListDto;
import com.example.domain.entry.Tag;
import com.example.domain.vo.PageVo;
import com.example.domain.vo.TagVo;
import com.example.domain.vo.TagVo2;
import com.example.mapper.TagMapper;
import com.example.service.TagService;
import com.example.utils.BeanCopyUtils;
import com.example.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2024-08-08 15:13:29
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<Tag>();
        wrapper.eq(StringUtils.hasText(tagListDto.getName()), Tag::getName, tagListDto.getName());
        wrapper.eq(StringUtils.hasText(tagListDto.getRemark()), Tag::getRemark, tagListDto.getRemark());
        Page<Tag> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public List<TagVo2> listAllTag() {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Tag::getId, Tag::getName);
        List<Tag> tags = list(wrapper);
        List<TagVo2> tagVo2s = BeanCopyUtils.copyBeanList(tags, TagVo2.class);
        return tagVo2s;
    }

    @Override
    public ResponseResult addTag(TagVo tag) {
        Tag tag1 = new Tag();
        tag1.setName(tag.getName());
        tag1.setRemark(tag.getRemark());
        Date date = new Date();
        tag1.setDelFlag(0);
        tag1.setCreateTime(date);
        tag1.setUpdateTime(date);
        Integer userId = SecurityUtils.getUserId();
        tag1.setCreateBy(Long.valueOf(userId));
        save(tag1);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTag(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTagInfo(Long id) {
        Tag tag = getById(id);
        TagVo tagVo = BeanCopyUtils.copyBean(tag, TagVo.class);
        return ResponseResult.okResult(tagVo);
    }


    @Override
    public ResponseResult updateTag(TagVo tag) {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tag::getId, tag.getId());
        Tag tag1 = new Tag();
        tag1.setName(tag.getName());
        tag1.setRemark(tag.getRemark());
        tag1.setUpdateTime(new Date());
        getBaseMapper().update(tag1, wrapper);
        return ResponseResult.okResult();
    }
}


