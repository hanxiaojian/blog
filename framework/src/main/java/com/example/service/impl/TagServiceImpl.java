package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.ResponseResult;
import com.example.domain.dto.TagListDto;
import com.example.domain.entity.Tag;
import com.example.domain.utils.BeanCopyUtils;
import com.example.domain.vo.PageVo;
import com.example.domain.vo.TagVo;
import com.example.mapper.TagMapper;
import com.example.service.TagService;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
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

    @Override
    public ResponseResult getTag(Long id) {
        Tag tag = getById(id);
        PageVo pageVo = BeanCopyUtils.beanCopy(tag, PageVo.class);
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult modifyTag(TagVo tagVo) {
        // 先按照标签ID查询出对应的信息
        LambdaQueryWrapper<Tag> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Tag::getId, tagVo.getId());
        Tag tag = BeanCopyUtils.beanCopy(tagVo, Tag.class);
        // 对结果进行修改
        update(tag, lambdaQueryWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public List<TagVo> listAllTag() {
        LambdaQueryWrapper<Tag> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(Tag::getId, Tag::getName, Tag::getRemark);
        List<Tag> list = list(lambdaQueryWrapper);
        List<TagVo> tagVos = BeanCopyUtils.beanCopyList(list, TagVo.class);
        return tagVos;
    }
}

