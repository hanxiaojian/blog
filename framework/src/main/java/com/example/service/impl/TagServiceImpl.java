package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.ResponseResult;
import com.example.domain.dto.TagListDto;
import com.example.domain.entity.Tag;
import com.example.domain.vo.PageVo;
import com.example.mapper.TagMapper;
import com.example.service.TagService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author hanxiaojian
 * @since 2023-04-15 14:40:30
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        // 查询结果
        LambdaQueryWrapper<Tag> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Tag::getName, tagListDto.getName());
        lambdaQueryWrapper.eq(Tag::getRemark, tagListDto.getRemark());
        // 分页
        Page<Tag> page = new Page<>(pageNum, pageSize);
        page(page, lambdaQueryWrapper);
        // 返回结果
        List<Tag> list = page.getRecords();
        long total = page.getTotal();
        return ResponseResult.okResult(new PageVo(list, total));
    }

    @Override
    public ResponseResult addTag(TagListDto tagListDto) {
        Tag tag = new Tag();
        tag.setName(tagListDto.getName());
        tag.setRemark(tagListDto.getRemark());
        // 保存数据
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult delTag(Long id) {
        // 根据标签ID删除数据
        removeById(id);
        return ResponseResult.okResult();
    }
}

