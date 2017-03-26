package com.free.blog.library.config;

import android.text.TextUtils;

import com.free.blog.BlogApplication;
import com.free.blog.library.util.SpfUtils;
import com.free.blog.model.entity.BlogCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author studiotang on 17/3/26
 */
public abstract class BaseUrlManger {
    private List<BlogCategory> mBlogCategories;

    protected abstract int getFirstIndex();

    protected abstract String getSpfKey();

    protected abstract String[] getUrls();

    BaseUrlManger() {
        init();
    }

    private void init() {
        mBlogCategories = new ArrayList<>();
        for (int i = getFirstIndex(); i < mNames.length; i++) {
            BlogCategory blogCategory = new BlogCategory();
            blogCategory.setName(mNames[i]);
            blogCategory.setLink(getUrls()[i]);
            mBlogCategories.add(blogCategory);
        }
    }

    public List<BlogCategory> getCategoryList() {
        return mBlogCategories;
    }

    public void saveType(BlogCategory blogCategory) {
        SpfUtils.put(BlogApplication.getContext(), getSpfKey(), blogCategory.getName());

    }

    public BlogCategory getType() {
        String columnType = (String) SpfUtils.get(BlogApplication.getContext(), getSpfKey(),
                getDefaultName());

        if (TextUtils.isEmpty(columnType)) {
            return getDefaultType();
        }
        for (BlogCategory blogCategory : mBlogCategories) {
            if (columnType.equals(blogCategory.getName())) {
                return blogCategory;
            }
        }
        return getDefaultType();
    }

    protected String getDefaultName() {
        return getUrls()[getFirstIndex()];
    }

    private BlogCategory getDefaultType() {
        BlogCategory blogCategory = new BlogCategory();
        blogCategory.setName(mNames[getFirstIndex()]);
        blogCategory.setLink(getUrls()[getFirstIndex()]);
        return blogCategory;
    }

    private String[] mNames = {
            UrlManager.CategoryName.ANDROID,
            UrlManager.CategoryName.MOBILE,
            UrlManager.CategoryName.WEB,
            UrlManager.CategoryName.ENTERPRISE,
            UrlManager.CategoryName.CODE,
            UrlManager.CategoryName.WWW,
            UrlManager.CategoryName.DATABASE,
            UrlManager.CategoryName.SYSTEM,
            UrlManager.CategoryName.CLOUD,
            UrlManager.CategoryName.SOFTWARE,
            UrlManager.CategoryName.OTHER
    };
}
