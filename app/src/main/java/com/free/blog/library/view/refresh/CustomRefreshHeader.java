package com.free.blog.library.view.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.free.blog.R;
import com.me.ui.widget.CircleLoadingView;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;


/**
 * @author studiotang on 17/4/1
 */
public class CustomRefreshHeader extends FrameLayout implements PtrUIHandler {

    private CircleLoadingView mCircleLoadingView;

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
        View header = LayoutInflater.from(getContext()).inflate(R.layout.loading_refresh_header, this);
        mCircleLoadingView = (CircleLoadingView) header.findViewById(R.id.custom_refresh_loading_view);
        mCircleLoadingView.setAnimListener(new CircleLoadingView.LeLoadingAnimListener() {
            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadFinished() {

            }
        });
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        mCircleLoadingView.disappearAnim(null);
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        mCircleLoadingView.appearAnim();
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
