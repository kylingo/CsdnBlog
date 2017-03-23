package com.free.blog.model.local.dao.impl;

import android.content.Context;

import com.free.blog.model.local.dao.BlogHistoryDao;

/**
 * @author tangqi on 17-3-23.
 */
public class BlogHistoryDaoImpl extends BlogCollectDaoImpl implements BlogHistoryDao {

    private static final String DB_NAME = "blog_history";

    public BlogHistoryDaoImpl(Context context) {
        super(context, DB_NAME);
    }
}
