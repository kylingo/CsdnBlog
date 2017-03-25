package com.free.blog.library.config;

import android.text.TextUtils;

import com.free.blog.BlogApplication;
import com.free.blog.library.util.SpfUtils;
import com.free.blog.model.entity.BlogCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author studiotang on 17/3/25
 */
public class ColumnManager {

    private List<BlogCategory> mBlogCategories;

    public ColumnManager() {
        init();
    }

    private void init() {
        mBlogCategories = new ArrayList<>();
        for (int i = 0; i < mChannelNameArray.length; i++) {
            BlogCategory blogCategory = new BlogCategory();
            blogCategory.setName(mChannelNameArray[i]);
            blogCategory.setLink(mUrls[i]);
            mBlogCategories.add(blogCategory);
        }
    }

    public List<BlogCategory> getCategoryList() {
        return mBlogCategories;
    }

    public void saveType(BlogCategory blogCategory) {
        SpfUtils.put(BlogApplication.getContext(), KeyConfig.COLUMN_TYPE, blogCategory.getName());

    }

    public BlogCategory getType() {
        String columnType = (String) SpfUtils.get(BlogApplication.getContext(), KeyConfig.COLUMN_TYPE,
                CategoryManager.CategoryName.ANDROID);

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

    private BlogCategory getDefaultType() {
        BlogCategory blogCategory = new BlogCategory();
        blogCategory.setName(mChannelNameArray[0]);
        blogCategory.setLink(mUrls[0]);
        return blogCategory;
    }

    private String[] mChannelNameArray = {
            CategoryManager.CategoryName.ANDROID,
            CategoryManager.CategoryName.MOBILE,
            CategoryManager.CategoryName.WEB,
            CategoryManager.CategoryName.ENTERPRISE,
            CategoryManager.CategoryName.CODE,
            CategoryManager.CategoryName.WWW,
            CategoryManager.CategoryName.DATABASE,
            CategoryManager.CategoryName.SYSTEM,
            CategoryManager.CategoryName.CLOUD,
            CategoryManager.CategoryName.SOFTWARE,
            CategoryManager.CategoryName.OTHER
    };

    private String[] mUrls = {
            CategoryManager.ColumnUrl.ANDROID,
            CategoryManager.ColumnUrl.MOBILE,
            CategoryManager.ColumnUrl.WEB,
            CategoryManager.ColumnUrl.ENTERPRISE,
            CategoryManager.ColumnUrl.CODE,
            CategoryManager.ColumnUrl.WWW,
            CategoryManager.ColumnUrl.DATABASE,
            CategoryManager.ColumnUrl.SYSTEM,
            CategoryManager.ColumnUrl.CLOUD,
            CategoryManager.ColumnUrl.SOFTWARE,
            CategoryManager.ColumnUrl.OTHER
    };
}
