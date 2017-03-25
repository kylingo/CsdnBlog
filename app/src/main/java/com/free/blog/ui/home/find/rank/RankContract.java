package com.free.blog.ui.home.find.rank;

import com.free.blog.ui.base.vp.refresh.IRefreshPresenter;
import com.free.blog.ui.base.vp.refresh.IRefreshView;

/**
 * @author studiotang on 17/3/25
 */
interface RankContract {
    interface View<T, P> extends IRefreshView<T, P> {

    }

    interface Presenter extends IRefreshPresenter {

    }
}
