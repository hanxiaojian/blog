package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.ResponseResult;
import com.example.domain.constants.SystemConstants;
import com.example.domain.entity.Link;
import com.example.domain.utils.BeanCopyUtils;
import com.example.domain.vo.LinkVo;
import com.example.mapper.LinkMapper;
import com.example.service.LinkService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author hanxiaojian
 * @since 2023-04-03 20:46:22
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> list = list(lambdaQueryWrapper);
        // 转换为对应Vo
        List<LinkVo> linkVos = BeanCopyUtils.beanCopyList(list, LinkVo.class);
        return ResponseResult.okResult(linkVos);
    }

}

