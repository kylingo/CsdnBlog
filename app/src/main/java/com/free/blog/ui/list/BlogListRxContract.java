package com.free.blog.ui.list;

import com.free.blog.ui.base.mvp.IBaseRefreshPresenter;
import com.free.blog.ui.base.mvp.IBaseRefreshView;

/**
 * @author tangqi on 17-3-20.
 */
class BlogListRxContract {

    interface View<T, P> extends IBaseRefreshView<T, P> {

    }

    interface Presenter extends IBaseRefreshPresenter {

        void setCategoryName(String category);

        void setCategoryLink(String categoryLink);
    }
}
