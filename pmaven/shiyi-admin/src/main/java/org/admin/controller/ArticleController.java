package org.admin.controller;

import com.example.domain.ResponseResult;
import com.example.domain.dto.AddArticleDto;
import com.example.domain.entry.Article;
import com.example.domain.vo.ArticleVo3;
import com.example.service.ArticleService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto addArticleDto) {
        return articleService.add(addArticleDto);
    }

    @GetMapping("/list")
    public ResponseResult list(@RequestParam("pageNum") Integer pageNum,
                               @RequestParam("pageSize")Integer pageSize,
                               AddArticleDto addArticleDto) {
        return articleService.adminArticleList(pageNum,pageSize,addArticleDto);
    }

    @GetMapping("/{id}")
    public ResponseResult get(@PathVariable("id") Long id) {
        return articleService.articleById(id);
    }
    @PutMapping
    public ResponseResult updateArticle(@RequestBody ArticleVo3 articleVo3) {
        return articleService.updateArticle(articleVo3);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteById(@PathVariable("id") Long id) {
//        if(!ids.contains(",")){
//            articleService.removeById(ids);
//        }else {
//            String [] idArray = ids.split(",");
//            for(String id : idArray){
//                articleService.removeById(id);
//            }
//        }
//        return ResponseResult.okResult();
        return  articleService.deleteById(id);
    }

}
