package com.free.blog.ui.list;

import com.free.blog.ui.base.mvp.refresh.IRefreshPresenter;
import com.free.blog.ui.base.mvp.refresh.IRefreshView;

/**
 * @author tangqi on 17-3-20.
 */
class BlogListContract {

    interface View<T, P> extends IRefreshView<T, P> {

    }

    interface Presenter extends IRefreshPresenter {

        void setCategoryName(String category);

        void setCategoryLink(String categoryLink);
    }
}
