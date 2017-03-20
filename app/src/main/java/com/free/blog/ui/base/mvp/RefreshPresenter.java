package com.free.blog.ui.base.mvp;

import com.free.blog.library.rx.RxSubscriber;

import rx.Observable;
import rx.Subscription;

/**
 * @author tangqi on 17-3-20.
 */
public abstract class RefreshPresenter<T> extends BaseRefreshPresenter {
    private int mPage = 1;
    private IBaseRefreshView<T, IBaseRefreshPresenter> mViewDelegate;
    private boolean isLoadRefresh;
    private boolean isLoadMore;

    public RefreshPresenter(IBaseRefreshView<T, IBaseRefreshPresenter> viewDelegate) {
        mViewDelegate = viewDelegate;
        mViewDelegate.setPresenter(this);
    }

    protected abstract Observable<? extends T> getObservable(int page);

    @Override
    protected Subscription loadRefreshSub() {
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

    @Override
    protected Subscription loadMoreSub() {
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
