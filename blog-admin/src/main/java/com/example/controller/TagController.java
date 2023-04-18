package com.example.controller;

import com.example.domain.ResponseResult;
import com.example.domain.dto.TagListDto;
import com.example.domain.vo.TagVo;
import com.example.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author：hanxiaojian
 * @Date：2023/4/15 14:42
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }

    @PostMapping()
    public ResponseResult addTag(@RequestBody TagListDto tagListDto) {
        return tagService.addTag(tagListDto);
    }

    @DeleteMapping ("/{id}")
    public ResponseResult delTag(@PathVariable("id") Long id) {
        return tagService.delTag(id);
    }

    @GetMapping("/{id}")
    public ResponseResult getTag(@PathVariable("id") Long id) {
        return tagService.getTag(id);
    }

    @PutMapping()
    public ResponseResult modifyTag(@RequestBody TagVo tagVo) {
        return tagService.modifyTag(tagVo);
    }
}
