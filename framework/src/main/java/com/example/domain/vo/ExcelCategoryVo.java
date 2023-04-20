package com.example.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author：hxj
 * @Date：2023/4/19 18:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelCategoryVo {
    @ExcelProperty("分类名")
    private String name;

    @ExcelProperty("描述")
    private String description;

    /**
     * 状态0:正常,1禁用
     */
    @ExcelProperty("状态0:正常,1禁用")
    private String status;
}
