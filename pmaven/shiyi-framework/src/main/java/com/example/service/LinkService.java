package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.ResponseResult;
import com.example.domain.entry.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2024-08-03 14:35:49
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult linkList(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult linkById(Long id);
}


