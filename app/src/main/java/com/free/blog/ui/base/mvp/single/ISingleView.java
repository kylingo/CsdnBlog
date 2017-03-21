package com.free.blog.ui.base.mvp.single;

import com.free.blog.ui.base.mvp.IBaseView;

/**
 * @author studiotang on 17/3/21
 */
public interface ISingleView<T, P> extends IBaseView<P> {
    void onUpdateUI(T t);

    void onUpdateFailure(int errNo);
}
