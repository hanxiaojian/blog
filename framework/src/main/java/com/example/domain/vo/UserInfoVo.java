package com.example.domain.vo;

import lombok.Data;

@Data
public class UserInfoVo {
    /**
     * 主键
     */
    private Long id;
    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    private String sex;

    private String email;
}
