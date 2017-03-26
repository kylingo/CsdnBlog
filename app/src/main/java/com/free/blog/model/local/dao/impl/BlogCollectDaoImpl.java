package com.free.blog.model.local.dao.impl;


import android.content.Context;

import com.free.blog.library.config.CacheManager;
import com.free.blog.model.entity.BlogItem;
import com.free.blog.model.local.dao.BlogCollectDao;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import java.util.List;

/**
 * 博客收藏-数据库实现
 *
 * @author Frank
 * @since 2015年8月13日下午12:58:51
 */

public class BlogCollectDaoImpl implements BlogCollectDao {
    private static final String DB_NAME = "collect_blog";
    private DbUtils db;

    public BlogCollectDaoImpl(Context context) {
        this(context, DB_NAME);
    }

    BlogCollectDaoImpl(Context context, String dbName) {
        db = DbUtils.create(context, CacheManager.getBloggerCollectDbPath(context), dbName);
    }


    @Override
    public void insert(List<BlogItem> list) {
        try {
            for (int i = 0; i < list.size(); i++) {
                BlogItem blogItem = list.get(i);
                BlogItem findItem = db.findFirst(Selector.from(BlogItem.class).where("link", "=", blogItem.getLink()));
                if (findItem != null) {
                    db.update(blogItem, WhereBuilder.b("link", "=", blogItem.getLink()));
                } else {
                    db.save(blogItem);
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(BlogItem blogItem) {
        try {
            BlogItem findItem = db.findFirst(Selector.from(BlogItem.class).where("link", "=", blogItem.getLink()));
            if (findItem != null) {
                db.update(blogItem, WhereBuilder.b("link", "=", blogItem.getLink()));
            } else {
                db.save(blogItem);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(BlogItem blogItem) {
        try {
            BlogItem findItem = db.findFirst(Selector.from(BlogItem.class).where("link", "=", blogItem.getLink()));
            if (findItem != null) {
                db.delete(findItem);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BlogItem query(String link) {
        try {
            return db.findFirst(Selector.from(BlogItem.class).where("link", "=", link));
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BlogItem> query(int page, int pageSize) {
        try {
            return db.findAll(Selector.from(BlogItem.class).orderBy("updateTime", true)
                    .limit(pageSize).offset((page - 1) * pageSize));
        } catch (DbException e) {
            e.printStackTrace();
        }

        return null;
    }

}
