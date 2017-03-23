package com.free.blog.ui.home.blogger;

import com.free.blog.model.entity.Blogger;
import com.free.blog.ui.base.mvp.refresh.IRefreshPresenter;
import com.free.blog.ui.base.mvp.refresh.IRefreshView;

/**
 * @author tangqi on 17-3-23.
 */
class BloggerContract {
    interface View<T, P> extends IRefreshView<T, P> {
        void addBloggerStart();

        void addBloggerSuccess(Blogger blogger);

        void addBloggerRepeat();

        void addBloggerFailure();

        void deleteBloggerSuccess();

        void deleteBloggerFailure();

        void stickBloggerSuccess(Blogger blogger);

        void stickBloggerFailure();
    }

    interface Presenter extends IRefreshPresenter {
        void addBlogger(String userId);

        void deleteBlogger(Blogger blogger);

        void stickBlogger(Blogger blogger);
    }
}
