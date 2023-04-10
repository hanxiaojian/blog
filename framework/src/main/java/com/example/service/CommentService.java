package com.example.service;

import com.example.domain.ResponseResult;

public interface CommentService {
    ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize);
}
