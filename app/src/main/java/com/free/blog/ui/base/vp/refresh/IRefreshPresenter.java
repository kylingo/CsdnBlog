package com.free.blog.ui.base.vp.refresh;

import com.free.blog.ui.base.vp.IBasePresenter;

/**
 * @author tangqi on 17-3-20.
 */
public interface IRefreshPresenter extends IBasePresenter {

    void loadRefreshData();

    void loadMoreData();

    int getPageSize();

    boolean hasMore(int size);
}
