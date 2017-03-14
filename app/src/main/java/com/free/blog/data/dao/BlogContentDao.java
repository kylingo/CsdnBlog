package com.free.blog.data.dao;

import com.free.blog.data.entity.BlogHtml;

/**
 * 博客内容-数据库
 * 
 * @author tangqi
 * @since 2015年8月7日下午11:47:16
 */

public interface BlogContentDao {

	/**
	 * 保存博客内容
	 */
	void insert(BlogHtml blogHtml);

	/**
	 * 获取博客内容
	 */
	BlogHtml query(String url);
}
