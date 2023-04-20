package com.example.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author：hxj
 * @Date：2023/4/20 17:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetArticleDto {
    private String title;
    private String summary;
}
