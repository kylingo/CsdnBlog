package com.free.csdn.db.impl;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.free.csdn.bean.BlogItem;
import com.free.csdn.bean.Blogger;
import com.free.csdn.db.BloggerDb;
import com.free.csdn.db.CacheManager;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

/**
 * 博主数据库
 * 
 * @author tangqi
 * @data 2015年8月8日下午12:17:35
 */

public class BloggerDbImpl implements BloggerDb {

	private DbUtils db;

	public BloggerDbImpl(Context context) {
		db = DbUtils.create(context, CacheManager.getBloggerDbPath(context),
				"Blogger");
	}

	public void insert(Blogger blogger) {
		try {
			Blogger findItem = db.findFirst(Selector.from(Blogger.class).where(
					"userId", "=", blogger.getUserId()));
			if (findItem != null) {
				db.update(blogger,
						WhereBuilder.b("userId", "=", blogger.getUserId()));
			} else {
				db.save(blogger);
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void insert(List<Blogger> list) {
		try {
			for (int i = 0; i < list.size(); i++) {
				Blogger blogger = list.get(i);
				BlogItem findItem = db.findFirst(Selector.from(Blogger.class)
						.where("userId", "=", blogger.getUserId()));
				if (findItem != null) {
					db.update(blogger,
							WhereBuilder.b("userId", "=", blogger.getUserId()));
				} else {
					db.save(blogger);
				}
			}

		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Blogger> queryAll() {
		try {
			// 最新的排最前面
			List<Blogger> list = db.findAll(Selector.from(Blogger.class)
					.orderBy("isNew", true));
			return list;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public List<Blogger> queryAll(String type) {
		try {
			// 最新的排最前面
			List<Blogger> list = new ArrayList<Blogger>();
			List<Blogger> newlist = db
					.findAll(Selector.from(Blogger.class)
							.where("type", "=", type)
							.where(WhereBuilder.b("isNew", "=", 1))
							.orderBy("updateTime", true));
			List<Blogger> oldlist = db.findAll(Selector.from(Blogger.class)
					.where("type", "=", type)
					.where(WhereBuilder.b("isNew", "=", 0)));
			list.addAll(newlist);
			list.addAll(oldlist);
			return list;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public List<Blogger> query(String type, int pageIndex, int pageSize) {
		List<Blogger> list = null;
		try {
			list = db.findAll(Selector.from(BlogItem.class)
					.where("type", "=", type).orderBy("isNew", true)
					.limit(pageSize).offset(pageIndex * pageSize));
			return list;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public void delete(Blogger blogger) {
		try {
			db.delete(blogger);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteAll(List<Blogger> list) {
		try {
			db.delete(list);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
