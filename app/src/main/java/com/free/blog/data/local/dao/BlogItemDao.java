package com.free.blog.data.local.dao;

import com.free.blog.data.entity.BlogCategory;
import com.free.blog.data.entity.BlogItem;

import java.util.List;


/**
 * 博客列表-数据库定义
 *
 * @author tangqi
 * @since 2015年8月7日下午11:19:46
 */

public interface BlogItemDao {

    /**
     * 保存博客列表
     */
    void insert(String category, List<BlogItem> blogItemList);

    /**
     * 查找博客列表
     */
    List<BlogItem> query(String category, int page);

    /**
     * 查询所有
     */
    List<BlogItem> queryAll();

    /**
     * 插入博客分类
     */
    void insertCategory(List<BlogCategory> blogCategoryList);

    /**
     * 查询博客分类
     */
    List<BlogCategory> queryCategory();

    /**
     * 删除所有
     */
    void deleteAll();
}
