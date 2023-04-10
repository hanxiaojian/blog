package com.example.service;

import com.example.domain.ResponseResult;
import com.example.domain.entity.Comment;

public interface CommentService {
    ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}
