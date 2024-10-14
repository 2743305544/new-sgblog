package org.admin.controller;


import com.example.domain.ResponseResult;
import com.example.domain.dto.TagListDto;
import com.example.domain.vo.PageVo;
import com.example.domain.vo.TagVo;
import com.example.domain.vo.TagVo2;
import com.example.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/tag")
public class TagController {


    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(@RequestParam("pageNum") Integer pageNum,
                                       @RequestParam("pageSize") Integer pageSize,
                                       @RequestParam(value = "name" ,required = false) String name,
                                       @RequestParam(value = "remark",required = false) String remark){
        TagListDto tagListDto = new TagListDto(name,remark);
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }

    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        List<TagVo2> list = tagService.listAllTag();
        return ResponseResult.okResult(list);
    }
    @PostMapping
    public ResponseResult addTag(@RequestBody TagVo tag){
        return tagService.addTag(tag);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable Long id){
        return tagService.deleteTag(id);
    }

    @GetMapping("/{id}")
    public ResponseResult getTagInfo(@PathVariable Long id){
        return tagService.getTagInfo(id);
    }
    @PutMapping()
    public ResponseResult updateTag(@RequestBody TagVo tag){
        return tagService.updateTag(tag);
    }
}
