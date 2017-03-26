package com.free.blog.ui.base.vp.menu;

import com.free.blog.library.config.UrlManager;
import com.free.blog.model.entity.BlogCategory;
import com.free.blog.ui.base.vp.refresh.IRefreshPresenter;
import com.free.blog.ui.base.vp.refresh.IRefreshView;
import com.free.blog.ui.base.vp.refresh.RefreshPresenter;

import java.util.List;

/**
 * @author studiotang on 17/3/26
 */
public abstract class MenuRefreshPresenter<T> extends RefreshPresenter<T> implements IMenuRefreshPresenter{

    protected UrlManager mNewBlogManager;
    protected BlogCategory mBlogCategory;

    protected abstract UrlManager getUrlManager();
    protected abstract void updateTitle(String title);

    public MenuRefreshPresenter(IRefreshView<T, IRefreshPresenter> viewDelegate) {
        super(viewDelegate);
        mNewBlogManager = getUrlManager();
        mBlogCategory = mNewBlogManager.getType();
    }

    @Override
    public String getTitle() {
        return mBlogCategory.getName();
    }

    @Override
    public List<BlogCategory> getCategoryList() {
        return mNewBlogManager.getCategoryList();
    }

    @Override
    public void setType(BlogCategory blogCategory) {
        mBlogCategory = blogCategory;
        mNewBlogManager.saveType(blogCategory);
        updateTitle(blogCategory.getName());
    }
}
