package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.constants.SystemConstants;
import com.example.domain.ResponseResult;
import com.example.domain.entry.Comment;
import com.example.domain.vo.CommentVo;
import com.example.domain.vo.PageVo;
import com.example.enums.AppHttpCodeEnum;
import com.example.excption.SystemException;
import com.example.mapper.CommentMapper;
import com.example.service.CommentService;
import com.example.service.UserService;
import com.example.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2024-08-04 14:32:09
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {


    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId, articleId);
        queryWrapper.eq(Comment::getRootId,-1);
        queryWrapper.eq(Comment::getType,commentType);
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());
        commentVoList.stream().map(commentVo -> {
            List<CommentVo> children = getChildren(commentVo.getId());
            commentVo.setChildren(children);
            return commentVo;
        }).collect(Collectors.toList());
        return ResponseResult.okResult(new PageVo(commentVoList,page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }


    private List<CommentVo> getChildren(Long parentId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId,parentId);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> list = list(queryWrapper);
        return toCommentVoList(list);
    }

    private List<CommentVo> toCommentVoList(List<Comment> commentList) {
        List<CommentVo> commentVoList = BeanCopyUtils.copyBeanList(commentList, CommentVo.class);
        commentVoList.stream().map(commentVo -> {
            commentVo.setUsername(userService.getById(commentVo.getCreateBy()).getNickName());
            if(commentVo.getToCommentUserId()!=-1){
                commentVo.setToCommentUserName(userService.getById(commentVo.getToCommentUserId()).getNickName());
            }
            return commentVo;
        }).collect(Collectors.toList());
        return commentVoList;
    }
}


