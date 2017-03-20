package com.free.blog.ui.base.mvp;

import com.free.blog.BlogApplication;
import com.free.blog.library.util.NetUtils;

import rx.Subscription;

/**
 * @author tangqi on 17-3-20.
 */
public abstract class BaseRefreshPresenter extends BasePresenter implements IBaseRefreshPresenter {

    protected abstract Subscription loadRefreshSub();

    protected abstract Subscription loadMoreSub();


    @Override
    public boolean isNetWorkAvailable() {
        return NetUtils.isNetAvailable(BlogApplication.getContext());
    }

    @Override
    public void loadRefreshData() {
        if (isSubscribed()) {
            addSubscription(loadRefreshSub());
        }
    }

    @Override
    public void loadMoreData() {
        if (isSubscribed()) {
            addSubscription(loadMoreSub());
        }
    }

}
