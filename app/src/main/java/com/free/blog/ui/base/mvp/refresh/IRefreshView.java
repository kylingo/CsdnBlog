package com.free.blog.ui.base.mvp.refresh;

import com.free.blog.ui.base.mvp.IBaseView;

/**
 * @author tangqi on 17-3-20.
 */
public interface IRefreshView<T, P> extends IBaseView<P> {

    void onRefreshUI(T data);

    void onRefreshFailure(int errNo);

    void onMoreUI(T data);

    void onMoreFailure(int errNo);
}
