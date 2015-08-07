package com.free.csdn.db;

import java.util.List;

import com.free.csdn.bean.BlogItem;

/**
 * 博客列表-数据库定义
 * 
 * @author tangqi
 * @data 2015年8月7日下午11:19:46
 */

public interface BlogListDb {

	/**
	 * 保存博客列表
	 * 
	 * @param list
	 */
	public void saveBlogList(List<BlogItem> list);

	/**
	 * 查找博客列表
	 * 
	 * @param page
	 * @return
	 */
	public List<BlogItem> findBlogList(int page);
}
