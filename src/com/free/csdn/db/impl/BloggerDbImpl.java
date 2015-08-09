package com.free.csdn.db.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.free.csdn.bean.BlogItem;
import com.free.csdn.bean.Blogger;
import com.free.csdn.db.BloggerDb;
import com.free.csdn.db.CacheManager;
import com.free.csdn.util.FileUtil;
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
	private String type;
	private Context context;

	public BloggerDbImpl(Context context, String type) {
		this.context = context;
		this.type = type;
		this.db = DbUtils.create(context, CacheManager.getBloggerDbPath(context), "blogger_" + type);
	}

	public void insert(Blogger blogger) {
		try {
			Blogger findItem = db.findFirst(Selector.from(Blogger.class).where("userId", "=", blogger.getUserId()));
			if (findItem != null) {
				db.update(blogger, WhereBuilder.b("userId", "=", blogger.getUserId()));
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
				BlogItem findItem = db
						.findFirst(Selector.from(Blogger.class).where("userId", "=", blogger.getUserId()));
				if (findItem != null) {
					db.update(blogger, WhereBuilder.b("userId", "=", blogger.getUserId()));
				} else {
					db.save(blogger);
				}
			}

		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Blogger query(String userId) {
		try {
			return db.findFirst(Selector.from(Blogger.class).where("userId", "=", userId));
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<Blogger> queryAll() {
		try {
			// 最新的排最前面
			List<Blogger> list = new ArrayList<Blogger>();
			List<Blogger> newlist = db.findAll(Selector.from(Blogger.class).where(WhereBuilder.b("isNew", "=", 1))
					.orderBy("updateTime", true));
			List<Blogger> oldlist = db.findAll(Selector.from(Blogger.class).where(WhereBuilder.b("isNew", "=", 0)));

			if (newlist != null) {
				list.addAll(newlist);
			}

			if (oldlist != null) {
				list.addAll(oldlist);
			}
			return list;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public List<Blogger> query(int pageIndex, int pageSize) {
		List<Blogger> list = null;
		try {
			list = db.findAll(Selector.from(BlogItem.class).orderBy("isNew", true).limit(pageSize)
					.offset(pageIndex * pageSize));
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

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

		// 删除数据库文件
		File file = new File(CacheManager.getBloggerDbPath(context) + "blogger_" + type);
		FileUtil.delete(file);

		file = new File(CacheManager.getBloggerDbPath(context) + "blogger_" + type + "-journal");
		FileUtil.delete(file);
	}
}
