package com.free.blog.library.view.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.free.blog.R;
import com.github.glomadrian.loadingballs.BallView;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;


/**
 * @author studiotang on 17/4/1
 */
public class BallRefreshHeader extends FrameLayout implements PtrUIHandler {

    private BallView mLoadingView;

    public BallRefreshHeader(Context context) {
        this(context, null);
    }

    public BallRefreshHeader(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public BallRefreshHeader(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        initView();
    }

    private void initView() {
        View header = LayoutInflater.from(getContext()).inflate(R.layout.loading_ball_header, this);
        mLoadingView = (BallView) header.findViewById(R.id.custom_ball_loading_view);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        mLoadingView.stop();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        mLoadingView.start();
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
