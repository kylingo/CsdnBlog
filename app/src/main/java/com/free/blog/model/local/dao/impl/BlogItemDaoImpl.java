package com.free.blog.model.local.dao.impl;

import android.content.Context;

import com.free.blog.model.entity.BlogCategory;
import com.free.blog.model.entity.BlogItem;
import com.free.blog.library.config.Config;
import com.free.blog.library.config.CacheManager;
import com.free.blog.model.local.dao.BlogItemDao;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import java.util.List;

/**
 * 博客列表-数据库实现
 *
 * @author tangqi
 * @since 2015年8月7日下午10:39:22
 */

public class BlogItemDaoImpl implements BlogItemDao {

    private DbUtils db;

    public BlogItemDaoImpl(Context context, String userId) {
        db = DbUtils.create(context, CacheManager.getBlogListDbPath(context), userId + "_blog");
    }

    @Override
    public void insert(String category, List<BlogItem> list) {
        try {
            for (int i = 0; i < list.size(); i++) {
                BlogItem blogItem = list.get(i);
                BlogItem findItem = db.findFirst(Selector.from(BlogItem.class).where("category", "=", category).and("link", "=", blogItem.getLink()));
                if (findItem != null) {
                    db.update(blogItem, WhereBuilder.b("category", "=", category).and("link", "=", blogItem.getLink()));
                } else {
                    db.save(blogItem);
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<BlogItem> query(String category, int page, int pageSize) {
        List<BlogItem> list = null;
        try {
            if (Config.BLOG_CATEGORY_ALL.equals(category)) {
                // 全部分类
//                list = db.findAll(Selector.from(BlogItem.class).where("category", "=", category).and("isTop", "=", 1));
                // 加上这句，可把置顶的文章在后面的地方不显示
                List<BlogItem> normalList = db.findAll(
                        Selector.from(BlogItem.class).where("category", "=", category)
                                .orderBy("isTop", true)
                                .limit(pageSize).offset((page - 1) * pageSize));
//                if (list != null) {
//                    list.addAll(normalList);
//                } else {
                    list = normalList;
//                }
            } else {
                // 其他分类
                list = db.findAll(Selector.from(BlogItem.class).where("category", "=", category).orderBy("date", true).limit(page * 20).offset((page - 1) * 20));
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<BlogItem> queryAll() {
        List<BlogItem> list = null;
        try {
            list = db.findAll(BlogItem.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void insertCategory(List<BlogCategory> blogCategoryList) {
        if (blogCategoryList == null) {
            return;
        }

        try {
            for (int i = 0; i < blogCategoryList.size(); i++) {
                BlogCategory blogCategory = blogCategoryList.get(i);
                BlogCategory findItem = db.findFirst(Selector.from(BlogCategory.class).where("name", "=", blogCategory.getName()));
                if (findItem != null) {
                    db.update(blogCategory, WhereBuilder.b("name", "=", blogCategory.getName()));
                } else {
                    db.save(blogCategory);
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<BlogCategory> queryCategory() {
        List<BlogCategory> list = null;
        try {
            list = db.findAll(Selector.from(BlogCategory.class));
        } catch (DbException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void deleteAll() {
        try {
            db.deleteAll(BlogItem.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}