package com.free.blog.ui.base.mvp;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @author tangqi on 17-3-20.
 */
public class BasePresenter implements IBasePresenter {

    private CompositeSubscription compositeSubscription;
    private boolean mIsSub = false;

    @Override
    public void subscribe() {
        mIsSub = true;
    }

    @Override
    public void unSubscribe() {
        mIsSub = false;
        clearSubscribe();
    }

    protected boolean isSubscribed() {
        return mIsSub;
    }

    protected void addSubscription(Subscription s) {
        if (s == null) {
            return;
        }
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
        }
        compositeSubscription.add(s);
    }

    private void clearSubscribe() {
        if (compositeSubscription != null && compositeSubscription.hasSubscriptions()) {
            compositeSubscription.clear();
        }
    }

}
