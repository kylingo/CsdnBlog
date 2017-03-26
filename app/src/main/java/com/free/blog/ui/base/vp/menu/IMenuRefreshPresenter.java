package com.free.blog.ui.base.vp.menu;

import com.free.blog.model.entity.BlogCategory;
import com.free.blog.ui.base.vp.refresh.IRefreshPresenter;

import java.util.List;

/**
 * @author studiotang on 17/3/26
 */
public interface IMenuRefreshPresenter extends IRefreshPresenter {
    String getTitle();

    List<BlogCategory> getCategoryList();

    void setType(BlogCategory blogCategory);
}
