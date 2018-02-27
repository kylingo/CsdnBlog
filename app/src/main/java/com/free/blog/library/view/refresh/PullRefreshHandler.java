package com.free.blog.library.view.refresh;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @author kylingo on 18/2/27
 */
public abstract class PullRefreshHandler extends PtrDefaultHandler {

    protected abstract void onRefresh();

    @Override
    public final void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
        onRefresh();
    }
}
