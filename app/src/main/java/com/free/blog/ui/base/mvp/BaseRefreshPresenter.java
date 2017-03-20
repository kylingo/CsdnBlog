package com.free.blog.ui.base.mvp;

import rx.Subscription;

/**
 * @author tangqi on 17-3-20.
 */
public abstract class BaseRefreshPresenter extends BasePresenter implements IBaseRefreshPresenter {

    protected abstract Subscription loadRefreshSub();

    protected abstract Subscription loadMoreSub();

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
