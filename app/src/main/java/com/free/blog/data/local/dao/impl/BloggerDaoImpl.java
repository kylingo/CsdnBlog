package com.free.blog.data.local.dao.impl;

import android.content.Context;

import com.free.blog.data.entity.Blogger;
import com.free.blog.data.local.dao.BloggerDao;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * 博主-数据库实现
 *
 * @author tangqi
 * @since 2015年8月8日下午12:17:35
 */

public class BloggerDaoImpl implements BloggerDao {

	private DbUtils db;
	private Context context;

	public BloggerDaoImpl(Context context, String type) {
		this.context = context;
		init(type);
	}

	public void init(String type) {
		this.db = DbUtils.create(context, "blogger_" + type);
	}

	@Override
	public void insert(Blogger blogger) {
		try {
			Blogger findItem = db.findFirst(Selector.from(Blogger.class).where("userId", "=", blogger.getUserId()));
			if (findItem != null) {
				db.update(blogger, WhereBuilder.b("userId", "=", blogger.getUserId()));
			} else {
				db.save(blogger);
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insert(List<Blogger> list) {
		try {
			db.saveOrUpdateAll(list);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Blogger query(String userId) {
		try {
			return db.findFirst(Selector.from(Blogger.class).where("userId", "=", userId));
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Blogger> queryAll() {
		try {
			// // 最新的排最前面
			List<Blogger> list = new ArrayList<Blogger>();
			List<Blogger> toplist = db.findAll(Selector.from(Blogger.class).where("isTop", "=", 1).orderBy("updateTime", true));
			List<Blogger> newlist = db.findAll(Selector.from(Blogger.class).where("isTop", "=", 0).and("isNew", "=", 1).orderBy("updateTime", true));
			List<Blogger> oldlist = db.findAll(Selector.from(Blogger.class).where("isTop", "=", 0).and("isNew", "=", 0));

			if (toplist != null) {
				list.addAll(toplist);
			}

			if (newlist != null) {
				list.addAll(newlist);
			}

			if (oldlist != null) {
				list.addAll(oldlist);
			}
			return list;
		} catch (DbException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Blogger> query(int pageIndex, int pageSize) {
		List<Blogger> list;
		try {
			list = db.findAll(Selector.from(Blogger.class).orderBy("isNew", true).limit(pageSize).offset(pageIndex * pageSize));
			return list;
		} catch (DbException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void delete(Blogger blogger) {
		try {
			db.delete(blogger);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteAll(List<Blogger> list) {
		try {
			db.delete(list);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteAll() {
		try {
			db.deleteAll(Blogger.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
}
