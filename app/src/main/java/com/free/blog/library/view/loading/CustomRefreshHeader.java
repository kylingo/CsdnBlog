package com.free.blog.library.view.loading;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;


/**
 * @author studiotang on 17/4/1
 */
public class CustomRefreshHeader extends FrameLayout implements PtrUIHandler {

//    SpinKitView spinKitView;
    public CustomRefreshHeader(Context context) {
        this(context, null);
    }

    public CustomRefreshHeader(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public CustomRefreshHeader(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        initView();
    }

    private void initView() {
//        View header = LayoutInflater.from(getContext()).inflate(R.layout.loading_refresh_header, this);
//        header.findViewById(R.id.spin_kit);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {

    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

    }
}
