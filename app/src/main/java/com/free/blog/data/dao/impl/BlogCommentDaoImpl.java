package com.free.blog.data.dao.impl;

import android.content.Context;

import com.free.blog.data.entity.Comment;
import com.free.blog.domain.config.CacheManager;
import com.free.blog.data.dao.BlogCommentDao;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import java.util.List;
/**
 * 博客评论-数据库实现
 * 
 * @author tangqi
 * @since 2015年8月7日下午11:53:19
 */

public class BlogCommentDaoImpl implements BlogCommentDao {

	private DbUtils db;

	public BlogCommentDaoImpl(Context context, String filename) {
		db = DbUtils.create(context, CacheManager.getBlogCommentDbPath(context),
				filename + "_comment");
	}

	@Override
	public void insert(List<Comment> list) {
		try {
			for (int i = 0; i < list.size(); i++) {
				Comment commentItem = list.get(i);
				Comment findItem = db.findFirst(Selector.from(Comment.class)
						.where("commentId", "=", commentItem.getCommentId()));
				if (findItem != null) {
					db.update(
							commentItem,
							WhereBuilder.b("commentId", "=",
									commentItem.getCommentId()));
				} else {
					db.save(commentItem);
				}
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Comment> query(int page) {
		try {
			return db.findAll(Selector.from(Comment.class).limit(
					20 * page));
		} catch (DbException e) {
			e.printStackTrace();
		}

		return null;
	}
}