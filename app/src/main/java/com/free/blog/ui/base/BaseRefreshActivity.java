package com.free.blog.ui.base;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.free.blog.R;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @author studiotang on 17/3/18
 */
public abstract class BaseRefreshActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    protected PtrFrameLayout mPtrFrameLayout;
    protected RecyclerView mRecyclerView;
    protected TextView mTvTitle;
    protected BaseViewAdapter mAdapter;

    protected abstract String getActionBarTitle();

    protected abstract BaseViewAdapter getAdapter();

    protected abstract void prepareData();

    protected abstract void loadData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_refresh);

        prepareData();
        initView();
    }

    private void initView() {
        initActionBar();

        mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.base_ptr_frame);
        mRecyclerView = (RecyclerView) findViewById(R.id.base_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = getAdapter();
        mAdapter.bindToRecyclerView(mRecyclerView);
        setEmptyView();

        PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(this);
        mPtrFrameLayout.addPtrUIHandler(header);
        mPtrFrameLayout.setHeaderView(header);
        mPtrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadData();
            }
        });

        mPtrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        }, 0);
    }

    private void initActionBar() {
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        ImageView mBackBtn = (ImageView) findViewById(R.id.btn_back);
        ImageView mMenuBtn = (ImageView) findViewById(R.id.btn_menu);
        mBackBtn.setOnClickListener(this);
        mMenuBtn.setOnClickListener(this);
        mMenuBtn.setVisibility(View.VISIBLE);
        mMenuBtn.setImageResource(R.drawable.ic_menu);

        setActionBarTitle(getActionBarTitle());
    }

    protected void setActionBarTitle(String title) {
        mTvTitle.setText(title);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;

            case R.id.btn_menu:
                showMenu(view);
                break;

            case R.id.iv_reload:
                refresh();
                break;

            default:
                break;
        }
    }

    protected void showMenu(View view) {

    }

    protected void refresh() {
        mPtrFrameLayout.autoRefresh(false);
    }

    protected void onRefreshComplete() {
        mPtrFrameLayout.refreshComplete();

        if (mAdapter.getItemCount() == 0) {
            mAdapter.isUseEmpty(true);
            mAdapter.notifyDataSetChanged();
        }
    }

    protected void setEmptyView() {
        mAdapter.setEmptyView(R.layout.empty_view_list);
        mAdapter.getEmptyView().findViewById(R.id.iv_reload).setOnClickListener(this);
        mAdapter.isUseEmpty(false);
    }
}
