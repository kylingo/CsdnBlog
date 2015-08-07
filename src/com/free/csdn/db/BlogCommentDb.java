package com.free.csdn.db;

import java.util.List;

import android.R.integer;

import com.free.csdn.bean.Comment;

/**
 * 博客评论数据库
 * 
 * @author tangqi
 * @data 2015年8月7日下午11:58:31
 */

public interface BlogCommentDb {

	public void saveCommentList(List<Comment> list);

	public List<Comment> getCommentList(int page);
}
