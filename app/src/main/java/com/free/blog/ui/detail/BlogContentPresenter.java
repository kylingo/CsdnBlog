package com.free.blog.ui.detail;

import com.free.blog.ui.base.mvp.single.ISinglePresenter;
import com.free.blog.ui.base.mvp.single.ISingleView;
import com.free.blog.ui.base.mvp.single.SinglePresenter;

import rx.Observable;

/**
 * @author studiotang on 17/3/21
 */
public class BlogContentPresenter<T> extends SinglePresenter<T> implements BlogContentContract.Presenter{

    public BlogContentPresenter(ISingleView<T, ISinglePresenter> viewDelegate) {
        super(viewDelegate);
    }

    @Override
    protected Observable getObservable() {
        return null;
    }
}
