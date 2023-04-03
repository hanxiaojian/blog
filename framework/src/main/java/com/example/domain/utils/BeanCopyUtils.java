package com.example.domain.utils;

import com.example.domain.entity.Article;
import com.example.domain.vo.HotArticleVo;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hxj
 */
public class BeanCopyUtils {
    // 前面<V>是泛型的定义，方便后面使用
    public static <V> V beanCopy(Object source, Class<V> clazz) {
        // 创建对象
        V result;
        try {
            result = clazz.newInstance();
            // 实现拷贝
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 返回结果
        return result;
    }

    public static <O, V> List<V> beanCopyList(List<O> list, Class<V> clazz) {
        return list.stream()
                .map(o -> beanCopy(o, clazz))
                .collect(Collectors.toList());
    }
}
