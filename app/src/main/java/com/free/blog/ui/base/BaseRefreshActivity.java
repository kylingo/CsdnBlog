package com.free.blog.ui.base;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.free.blog.R;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @author studiotang on 17/3/18
 */
public class BaseRefreshActivity extends BaseActivity {

    protected PtrFrameLayout mPtrFrameLayout;
    protected RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_refresh);

        initView();
    }

    private void initView() {
        mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.base_ptr_frame);
        mRecyclerView = (RecyclerView) findViewById(R.id.base_recycler_view);

        PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(this);
        mPtrFrameLayout.addPtrUIHandler(header);
        mPtrFrameLayout.setHeaderView(header);

        mPtrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

            }
        });
    }
}
