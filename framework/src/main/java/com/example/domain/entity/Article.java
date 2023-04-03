package com.example.domain.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * (Blog)表实体类
 *
 * @author hanxiaojian
 * @since 2023-03-26 17:40:55
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    private Long id;
    // 文章标题
    private String title;
    // 文章内容
    private String content;
    // 文章类型：1文章 2草稿
    private String type;
    // 文章摘要
    private String summary;
    // 所属分类ID
    private Long categoryId;
    @TableField(exist = false)
    private String categoryName;
    // 缩略图
    private String thumbnail;
    // 是否置顶：0否 1是
    private String isTop;
    // 是否发布：0已发布 1草稿
    private String status;
    // 评论数
    private Integer commentCount;
    //访问量
    private Long viewCount;
    // 是否允许评论：1是 0否
    private String isComment;
    
    private Long createBy;
    
    private Date createTime;
    
    private Long updateBy;
    
    private Date updateTime;
    // 删除标志：0未删除 1已删除
    private Integer delFlag;

}

