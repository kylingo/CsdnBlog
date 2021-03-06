package com.free.blog.model.local.dao;

import com.free.blog.model.entity.Blogger;

import java.util.List;

/**
 * 博主数据库
 * 
 * @author tangqi
 * @since 2015年8月8日下午12:59:26
 */

public interface ChannelBloggerDao {

	/**
	 * 插入博主
	 */
	void insert(Blogger blogger);

	/**
	 * 插入博主列表
	 */
	void insert(List<Blogger> list);

	/**
	 * 查询某个博主是否存在
	 */
	Blogger query(String userId);

	/**
	 * 查询所有博主
	 */
	@SuppressWarnings("unused")
	List<Blogger> queryAll();

	/**
	 * 查询博主（分页）
	 */
	List<Blogger> query(int pageIndex, int pageSize);

	/**
	 * 删除博主
	 */
	void delete(Blogger blogger);

	/**
	 * 删除博主列表
	 */
	@SuppressWarnings("unused")
	void deleteAll(List<Blogger> list);

	/**
	 * 删除所有博主
	 */
	@SuppressWarnings("unused")
	void deleteAll();
}
