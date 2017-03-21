package com.free.blog.ui.detail;

import com.free.blog.ui.base.mvp.single.ISinglePresenter;
import com.free.blog.ui.base.mvp.single.ISingleView;

/**
 * @author studiotang on 17/3/21
 */
public interface BlogContentContract {
    interface View<T, P> extends ISingleView<T, P> {

    }

    interface Presenter extends ISinglePresenter {

    }
}
