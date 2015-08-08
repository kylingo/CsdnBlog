package com.free.csdn.db.impl;

import java.util.List;

import android.content.Context;

import com.free.csdn.bean.BlogItem;
import com.free.csdn.db.BlogListDb;
import com.free.csdn.db.CacheManager;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

/**
 * 博客列表数据库
 * 
 * @author tangqi
 * @data 2015年8月7日下午10:39:22
 */

public class BlogListDbImpl implements BlogListDb {

	private DbUtils db;

	public BlogListDbImpl(Context context, String userId) {
		// TODO Auto-generated constructor stub
		db = DbUtils.create(context, CacheManager.getBlogListDbPath(context),
				userId + "_blog");
	}

	public void insert(List<BlogItem> list) {
		try {
			for (int i = 0; i < list.size(); i++) {
				BlogItem blogItem = list.get(i);
				BlogItem findItem = db.findFirst(Selector.from(BlogItem.class)
						.where("link", "=", blogItem.getLink()));
				if (findItem != null) {
					db.update(blogItem,
							WhereBuilder.b("link", "=", blogItem.getLink()));
				} else {
					db.save(blogItem);
				}
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<BlogItem> query(int page) {
		List<BlogItem> list = null;
		try {
			list = db.findAll(Selector.from(BlogItem.class).where("isTop", "=",
					1));

			// 加上这句，可把置顶的文章在后面的地方不显示
			List<BlogItem> normalList = db.findAll(Selector
					.from(BlogItem.class).where("isTop", "!=", 1)
					.orderBy("date", true).limit(page * 20));
			if (list != null) {
				list.addAll(normalList);
			} else {
				list = normalList;
			}

		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
}
