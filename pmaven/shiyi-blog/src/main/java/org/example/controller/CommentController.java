package org.example.controller;


import com.example.constants.SystemConstants;
import com.example.domain.ResponseResult;
import com.example.domain.entry.Comment;
import com.example.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult commentList(@RequestParam("articleId") Long articleId,@RequestParam("pageNum")Integer pageNum,@RequestParam("pageSize")Integer pageSize){
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }

    @PostMapping
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }


    @GetMapping("/linkCommentList")
    public ResponseResult linkCommentList(@RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize")Integer pageSize){
        return commentService.commentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }
}
