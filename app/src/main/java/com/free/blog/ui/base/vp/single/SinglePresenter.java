package com.free.blog.ui.base.vp.single;

import com.free.blog.library.rx.RxHelper;
import com.free.blog.library.rx.RxSubscriber;
import com.free.blog.ui.base.vp.BasePresenter;

import rx.Observable;
import rx.Subscription;

/**
 * @author studiotang on 17/3/21
 */
public abstract class SinglePresenter<T> extends BasePresenter implements ISinglePresenter {

    protected ISingleView<T, ISinglePresenter> mViewDelegate;

    public SinglePresenter(ISingleView<T, ISinglePresenter> viewDelegate) {
        this.mViewDelegate = viewDelegate;
        mViewDelegate.setPresenter(this);
    }

    protected abstract Observable<T> getObservable();

    @Override
    public void loadData() {
        if (isSubscribed()) {
            addSubscription(loadSub());
        }
    }

    private Subscription loadSub() {
        return getObservable()
                .compose(RxHelper.<T>getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<T>() {
                    @Override
                    public void onError(Throwable e) {
                        mViewDelegate.onUpdateFailure(0);
                    }

                    @Override
                    public void onNext(T t) {
                        mViewDelegate.onUpdateUI(t);
                    }
                });
    }
}