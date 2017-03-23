package com.free.blog.ui.base.fragment;

import com.free.blog.ui.base.activity.IBaseRefresh;

/**
 * @author tangqi on 17-3-23.
 */
public interface IBaseRefreshFragment extends IBaseRefresh {
    boolean enableRefresh();

    boolean enableLoadMore();
}
