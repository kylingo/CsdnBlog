package com.free.blog.model.local.dao.impl;

import android.content.Context;

import com.free.blog.model.local.dao.BlogContentDao;
import com.free.blog.model.entity.BlogHtml;
import com.free.blog.library.config.CacheManager;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

/**
 * 博客内容-数据库实现
 * 
 * @author tangqi
 * @since 2015年8月7日下午11:24:06
 */

public class BlogContentDaoImpl implements BlogContentDao {

	private DbUtils db;

	public BlogContentDaoImpl(Context context) {
		db = DbUtils.create(context, CacheManager.getBlogContentDbPath(context), "blog_content");
	}

	@Override
	public void insert(BlogHtml blogHtml) {
		BlogHtml findItem;
		try {
			findItem = db.findFirst(Selector.from(BlogHtml.class).where("url",
					"=", blogHtml.getUrl()));

			if (findItem != null) {
				db.update(blogHtml,
						WhereBuilder.b("url", "=", blogHtml.getUrl()));
			} else {
				db.save(blogHtml);
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	@Override
	public BlogHtml query(String url) {
		BlogHtml blogHtml = null;
		try {
			blogHtml = db.findFirst(Selector.from(BlogHtml.class).where("url", "=", url));
		} catch (DbException e) {
			e.printStackTrace();
		}
		return blogHtml;
	}
}
