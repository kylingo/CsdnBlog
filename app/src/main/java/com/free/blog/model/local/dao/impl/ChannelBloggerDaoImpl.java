package com.free.blog.model.local.dao.impl;

import android.content.Context;

import com.free.blog.library.config.CacheManager;
import com.free.blog.model.entity.Blogger;
import com.free.blog.model.local.dao.ChannelBloggerDao;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * 某频道博客专家-数据库实现
 *
 * @author tangqi
 * @since 2015年9月25日下午16:50:35
 */

public class ChannelBloggerDaoImpl implements ChannelBloggerDao {

    private DbUtils db;

    public ChannelBloggerDaoImpl(Context context, String channelName) {
        this.db = DbUtils.create(context, CacheManager.getChannelBloggerDbPath(context), channelName);
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
            List<Blogger> list = new ArrayList<>();
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
    public List<Blogger> query(int page, int pageSize) {
        List<Blogger> list;
        try {
            list = db.findAll(Selector.from(Blogger.class).orderBy("isNew", true)
                    .limit(pageSize).offset((page - 1) * pageSize));
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
