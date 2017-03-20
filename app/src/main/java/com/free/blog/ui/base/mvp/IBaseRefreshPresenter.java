package com.free.blog.ui.base.mvp;

/**
 * @author tangqi on 17-3-20.
 */
public interface IBaseRefreshPresenter extends IBasePresenter {

    boolean isNetWorkAvailable();

    void loadRefreshData();

    void loadMoreData();
}
