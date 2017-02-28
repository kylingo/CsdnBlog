package com.free.blog.model;

import com.free.blog.domain.bean.BlogCategory;
import com.free.blog.domain.bean.BlogItem;

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
	public void insert(String category, List<BlogItem> blogItemList);

	/**
	 * 查找博客列表
	 *
	 * @param page
	 * @return
	 */
	public List<BlogItem> query(String category, int page);

	/**
	 * 查询所有
	 *
	 * @return
	 */
	public List<BlogItem> queryAll();

	/**
	 * 插入博客分类
	 */
	public void insertCategory(List<BlogCategory> blogCategoryList);

	/**
	 * 查询博客分类
	 */
	public List<BlogCategory> queryCategory();

	/**
	 * 删除所有
	 */
	public void deleteAll();
}
