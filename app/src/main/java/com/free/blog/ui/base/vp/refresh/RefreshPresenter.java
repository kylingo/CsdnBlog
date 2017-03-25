package com.free.blog.ui.base.vp.refresh;

import com.free.blog.library.rx.RxSubscriber;
import com.free.blog.ui.base.vp.BasePresenter;

import rx.Observable;
import rx.Subscription;

/**
 * @author tangqi on 17-3-20.
 */
public abstract class RefreshPresenter<T> extends BasePresenter implements IRefreshPresenter {

    private static final int DEFAULT_PAGE_SIZE = 20;
    protected IRefreshView<T, IRefreshPresenter> mViewDelegate;
    private int mPage = 1;
    private boolean isLoadRefresh;
    private boolean isLoadMore;

    public RefreshPresenter(IRefreshView<T, IRefreshPresenter> viewDelegate) {
        mViewDelegate = viewDelegate;
        mViewDelegate.setPresenter(this);
    }

    protected IRefreshView<T, IRefreshPresenter> getViewDelegate() {
        return mViewDelegate;
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

    @Override
    public int getPageSize() {
        return DEFAULT_PAGE_SIZE;
    }

    @Override
    public boolean hasMore(int fetchCount) {
        return fetchCount >= getPageSize();
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
