package com.free.blog.ui.base.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.free.blog.R;
import com.free.blog.ui.base.adapter.BaseViewAdapter;
import com.free.blog.ui.base.mvp.refresh.IRefreshPresenter;
import com.free.blog.ui.base.mvp.refresh.IRefreshView;
import com.free.blog.ui.base.mvp.refresh.RefreshPresenter;

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @author studiotang on 17/3/18
 */
@SuppressWarnings("unchecked")
public abstract class BaseRefreshActivity<T> extends BaseActivity implements
        IRefreshView<T, IRefreshPresenter>, IBaseRefreshPage, View.OnClickListener,
        BaseQuickAdapter.RequestLoadMoreListener {

    protected RefreshPresenter mPresenter;

    protected PtrFrameLayout mPtrFrameLayout;
    protected RecyclerView mRecyclerView;
    protected TextView mTvTitle;
    protected BaseViewAdapter mAdapter;

    protected abstract String getActionBarTitle();

    protected abstract void beforeInitView();

    protected abstract BaseViewAdapter getAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_refresh);

        beforeInitView();
        initView();
    }

    protected void initView() {
        initActionBar();

        mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.base_ptr_frame);
        mRecyclerView = (RecyclerView) findViewById(R.id.base_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                BaseRefreshActivity.this.onSimpleItemClick(adapter, view, position);
            }
        });

        mAdapter = getAdapter();
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        setEmptyView();

        PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(this);
        mPtrFrameLayout.addPtrUIHandler(header);
        mPtrFrameLayout.setHeaderView(header);
        mPtrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadInitData();
            }
        });

        mPtrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                doRefresh();
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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }

    @Override
    public void setPresenter(IRefreshPresenter presenter) {
        mPresenter = (RefreshPresenter) presenter;
        mPresenter.subscribe();
    }

    @Override
    public void onLoadMoreRequested() {
        loadMoreData();
    }

    protected void doRefresh() {
        mPtrFrameLayout.autoRefresh(false);
        mAdapter.isUseEmpty(false);
    }

    @Override
    public void loadInitData() {
        mPresenter.loadRefreshData();
    }

    @Override
    public void loadMoreData() {
        mPresenter.loadMoreData();
    }

    @Override
    public void onRefreshUI(T data) {
        mAdapter.setNewData((List<T>) data);
        onRefreshComplete();
    }

    @Override
    public void onRefreshFailure(int errNo) {
        onRefreshComplete();
    }

    @Override
    public void onMoreUI(T data) {
        List<T> list = (List<T>) data;
        if (list != null) {
            mAdapter.addData(list);
            mAdapter.loadMoreComplete();
        } else {
            mAdapter.loadMoreFail();
        }
    }

    @Override
    public void onMoreFailure(int errNo) {
        mAdapter.loadMoreFail();
        mAdapter.setEnableLoadMore(false);
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
                doRefresh();
                break;

            default:
                break;
        }
    }

    protected void showMenu(View view) {

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

    protected void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
