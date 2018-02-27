package com.free.blog.library.view.refresh;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 下拉刷新控件-封装
 *
 * @author kylingo on 18/2/27
 */
public class PullRefreshLayout extends PtrFrameLayout {

    public PullRefreshLayout(Context context) {
        this(context, null);
    }

    public PullRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    protected void init() {
        addHeaderView();
    }

    protected void addHeaderView() {
//        PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(getActivity());
//        header.setLastUpdateTimeKey(KeyConfig.UPDATE_TIME);
        CustomRefreshHeader header = new CustomRefreshHeader(getContext());
        addPtrUIHandler(header);
        setHeaderView(header);
    }
}
