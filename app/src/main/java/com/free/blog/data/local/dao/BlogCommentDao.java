package com.free.blog.data.local.dao;


import com.free.blog.data.entity.Comment;

import java.util.List;

/**
 * 博客评论数据库
 *
 * @author tangqi
 * @since 2015年8月7日下午11:58:31
 */

public interface BlogCommentDao {

    /**
     * 保存博客评论列表
     */
    void insert(List<Comment> list);

    /**
     * 获取博客评论列表
     */
    List<Comment> query(int page);
}
