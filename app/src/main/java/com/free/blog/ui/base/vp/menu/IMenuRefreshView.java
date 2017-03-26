package com.free.blog.ui.base.vp.menu;

import com.free.blog.ui.base.vp.refresh.IRefreshView;

/**
 * @author studiotang on 17/3/26
 */
public interface IMenuRefreshView<T, P> extends IRefreshView<T, P> {
    void updateTitle(String title);
}
