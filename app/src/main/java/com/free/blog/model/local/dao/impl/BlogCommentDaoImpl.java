package com.free.blog.model.local.dao.impl;

import android.content.Context;

import com.free.blog.library.config.CacheManager;
import com.free.blog.model.entity.Comment;
import com.free.blog.model.local.dao.BlogCommentDao;
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

    public BlogCommentDaoImpl(Context context, String blogId) {
        db = DbUtils.create(context, CacheManager.getBlogCommentDbPath(context),
                blogId + "_comment");
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
    public List<Comment> query(int page, int pageSize) {
        try {
            return db.findAll(Selector.from(Comment.class).limit(
                    pageSize).offset((page - 1) * pageSize));
        } catch (DbException e) {
            e.printStackTrace();
        }

        return null;
    }
}
