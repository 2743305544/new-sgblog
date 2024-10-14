package org.admin.controller;


import com.example.domain.ResponseResult;
import com.example.domain.entry.Link;
import com.example.service.LinkService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/link")
public class LinkController {
    @Resource
    private LinkService linkService;

    @GetMapping("/list")
    public ResponseResult linkList(@RequestParam("pageNum") Integer pageNum,
                                   @RequestParam("pageSize") Integer pageSize,
                                   @RequestParam(value = "name",required = false) String name,
                                   @RequestParam(value = "status",required = false) String status){
        return linkService.linkList(pageNum,pageSize,name,status);
    }
    @PostMapping
    public ResponseResult addLink(@RequestBody Link link){
        linkService.save(link);
        return ResponseResult.okResult();
    }

    @GetMapping("/{id}")
    public ResponseResult linkById(@PathVariable("id") Long id){
        return linkService.linkById(id);
    }
    @PutMapping
    public ResponseResult updateLink(@RequestBody Link link){
        linkService.updateById(link);
        return ResponseResult.okResult();
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteLink(@PathVariable("id") Long id){
        linkService.removeById(id);
        return ResponseResult.okResult();
    }
}
