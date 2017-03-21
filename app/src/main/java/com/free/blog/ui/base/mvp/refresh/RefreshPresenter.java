package com.free.blog.ui.base.mvp.refresh;

import com.free.blog.library.rx.RxSubscriber;
import com.free.blog.ui.base.mvp.BasePresenter;

import rx.Observable;
import rx.Subscription;

/**
 * @author tangqi on 17-3-20.
 */
public abstract class RefreshPresenter<T> extends BasePresenter implements IRefreshPresenter{
    private int mPage = 1;
    private IRefreshView<T, IRefreshPresenter> mViewDelegate;
    private boolean isLoadRefresh;
    private boolean isLoadMore;

    public RefreshPresenter(IRefreshView<T, IRefreshPresenter> viewDelegate) {
        mViewDelegate = viewDelegate;
        mViewDelegate.setPresenter(this);
    }

    protected abstract Observable<? extends T> getObservable(int page);

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

    private Subscription loadRefreshSub() {
        if (isLoadRefresh) {
            return null;
        }

        isLoadRefresh = true;
        return getObservable(1)
                .subscribe(new RxSubscriber<T>() {
                    @Override
                    public void onError(Throwable e) {
                        mViewDelegate.onRefreshFailure(-1);
                        isLoadRefresh = false;
                    }

                    @Override
                    public void onCompleted() {
                        mPage = 2;
                        isLoadRefresh = false;
                    }

                    @Override
                    public void onNext(T t) {
                        mViewDelegate.onRefreshUI(t);
                    }
                });
    }

    private Subscription loadMoreSub() {
        if (isLoadMore) {
            return null;
        }

        isLoadMore = true;
        return getObservable(mPage)
                .subscribe(new RxSubscriber<T>() {
                    @Override
                    public void onError(Throwable e) {
                        mViewDelegate.onMoreFailure(-1);
                        isLoadMore = false;
                    }

                    @Override
                    public void onCompleted() {
                        mPage++;
                        isLoadMore = false;
                    }

                    @Override
                    public void onNext(T t) {
                        mViewDelegate.onMoreUI(t);

                    }
                });
    }
}
