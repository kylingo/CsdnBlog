package com.free.blog.model.local.dao;


import com.free.blog.model.entity.BlogItem;

import java.util.List;

/**
 * 博客收藏-数据库定义
 *
 * @author tangqi
 * @since 2015年8月7日下午11:19:46
 */

public interface BlogCollectDao {

    /**
     * 保存博客列表
     */
    void insert(List<BlogItem> list);

    /**
     * 保存博客
     */
    void insert(BlogItem blogItem);

    /**
     * 删除博客
     */
    void delete(BlogItem blogItem);

    /**
     * 查找博客
     */
    BlogItem query(String link);

    /**
     * 查找博客列表
     */
    List<BlogItem> query(int page, int pageSize);
}
