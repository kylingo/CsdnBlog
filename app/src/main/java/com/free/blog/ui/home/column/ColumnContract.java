package com.free.blog.ui.home.column;

import com.free.blog.model.entity.BlogCategory;
import com.free.blog.ui.base.vp.refresh.IRefreshPresenter;
import com.free.blog.ui.base.vp.refresh.IRefreshView;

import java.util.List;

/**
 * @author studiotang on 17/3/25
 */
class ColumnContract {

    interface View<T, P> extends IRefreshView<T, P> {
        void updateTitle(String title);
    }

    interface Presenter extends IRefreshPresenter {
        String getTitle();

        List<BlogCategory> getCategoryList();

        void setType(BlogCategory blogCategory);
    }
}
