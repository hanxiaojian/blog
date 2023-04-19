package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.ResponseResult;
import com.example.domain.dto.TagListDto;
import com.example.domain.entity.Tag;
import com.example.domain.vo.TagVo;

import java.util.List;


/**
 * 标签(Tag)表服务接口
 *
 * @author hanxiaojian
 * @since 2023-04-15 14:40:28
 */
public interface TagService extends IService<Tag> {

    ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(TagListDto tagListDto);

    ResponseResult delTag(Long id);

    ResponseResult getTag(Long id);

    ResponseResult modifyTag(TagVo tagVo);

    /**
     * 查询所有标签
     * @return
     */
    List<TagVo> listAllTag();
}

