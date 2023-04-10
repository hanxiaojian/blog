package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.ResponseResult;
import com.example.domain.entity.Article;
import com.example.domain.entity.Comment;
import com.example.domain.entity.User;
import com.example.domain.utils.BeanCopyUtils;
import com.example.domain.vo.CommentVo;
import com.example.domain.vo.PageVo;
import com.example.mapper.CommentMapper;
import com.example.service.ArticleService;
import com.example.service.CommentService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("commentService")
public class commentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private UserService userService;
    @Override
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 相当于把articleId和rootId当作sql查询条件查询出符合条件的结果
        lambdaQueryWrapper.eq(Comment::getArticleId, articleId);
        lambdaQueryWrapper.eq(Comment::getRootId, -1);
        // 分页查询
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, lambdaQueryWrapper);
        // 处理返回结果
        List<CommentVo> commentVos = toCommentVoList(page.getRecords());
        // 获取子评论
        for (CommentVo commentVo : commentVos) {
            Long id = commentVo.getId();
            List<CommentVo> commentVoList = getChildren(id);
            // 赋值
            commentVo.setChildren(commentVoList);
        }
        return ResponseResult.okResult(new PageVo(commentVos, page.getTotal()));
    }

    private List<CommentVo> getChildren(Long id) {
        // 编写sql语句
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getRootId, id);
        lambdaQueryWrapper.orderByDesc(Comment::getCreateTime);
        // 获取查询结果
        List<Comment> commentList = list(lambdaQueryWrapper);
        List<CommentVo> commentVos = toCommentVoList(commentList);
        return commentVos;
    }

    /**
     * 转成vo返回类型
     * @param commentList
     * @return
     */
    private List<CommentVo> toCommentVoList(List<Comment> commentList) {
        List<CommentVo> commentVos = BeanCopyUtils.beanCopyList(commentList, CommentVo.class);
        for (CommentVo commentVo : commentVos) {
            User user = userService.getById(commentVo.getCreateBy());
            commentVo.setUsername(user.getNickName());
            // 通过toCommentUserId查询目标用户的昵称并赋值
            // 如果toCommentUserId不为-1才进行查询
            if (commentVo.getToCommentUserId() != -1) {
                User usr = userService.getById(commentVo.getToCommentUserId());
                commentVo.setToCommentUserName(usr.getNickName());
            }
        }
        return commentVos;
    }
}
