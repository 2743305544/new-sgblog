package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.constants.SystemConstants;
import com.example.domain.ResponseResult;
import com.example.domain.entry.Link;
import com.example.domain.vo.LinkVo;
import com.example.domain.vo.LinkVo2;
import com.example.domain.vo.PageVo;
import com.example.mapper.LinkMapper;
import com.example.service.LinkService;
import com.example.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2024-08-03 14:35:50
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {


    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<Link>();
        queryWrapper.eq(Link::getStatus, SystemConstants.Link_STATUS_NORMAL);
        List<Link> list = list(queryWrapper);
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(list, LinkVo.class);
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult linkList(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(status),Link::getStatus, status);
        queryWrapper.like(Objects.nonNull(name),Link::getName, name);
        Page<Link> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<LinkVo2> linkVo2s = BeanCopyUtils.copyBeanList(page.getRecords(), LinkVo2.class);
        return ResponseResult.okResult(new PageVo(linkVo2s, page.getTotal()));
    }

    @Override
    public ResponseResult linkById(Long id) {
        Link link = this.getById(id);
        LinkVo2 linkVo2 = BeanCopyUtils.copyBean(link, LinkVo2.class);
        return ResponseResult.okResult(linkVo2);
    }
}


