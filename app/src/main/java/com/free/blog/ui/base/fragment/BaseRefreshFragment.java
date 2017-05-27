package com.free.blog.ui.base.fragment;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.free.blog.R;
import com.free.blog.library.view.recyclerview.DividerItemDecoration;
import com.free.blog.library.view.refresh.BallRefreshHeader;
import com.free.blog.ui.base.adapter.BaseViewAdapter;
import com.free.blog.ui.base.vp.refresh.IRefreshPresenter;
import com.free.blog.ui.base.vp.refresh.IRefreshView;
import com.free.blog.ui.base.vp.refresh.RefreshPresenter;

import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @author tangqi on 17-3-23.
 */
public abstract class BaseRefreshFragment<T> extends BaseFragment implements
        IRefreshView<T, IRefreshPresenter>, IBaseRefreshFragment,
        BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener,
        BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemLongClickListener{

    protected RefreshPresenter mPresenter;
    protected PtrFrameLayout mPtrFrameLayout;
    protected RecyclerView mRecyclerView;
    protected BaseViewAdapter mAdapter;
    protected TextView mTvTitle;

    protected abstract String getActionBarTitle();

    protected abstract void beforeInitView();

    protected abstract BaseViewAdapter onCreateAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        beforeInitView();
        return inflater.inflate(R.layout.common_base_refresh, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initView(view);
    }

    protected void initView(View view) {
        initActionBar(view);

        mPtrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.base_ptr_frame);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.base_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                LinearLayoutManager.VERTICAL, getResources().getColor(R.color.line)));

        mAdapter = onCreateAdapter();
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemLongClickListener(this);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setEnableLoadMore(enableLoadMore());
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);

//        PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(getActivity());
//        header.setLastUpdateTimeKey(KeyConfig.UPDATE_TIME);
        BallRefreshHeader header = new BallRefreshHeader(getActivity());
        mPtrFrameLayout.addPtrUIHandler(header);
        mPtrFrameLayout.setHeaderView(header);
        mPtrFrameLayout.setPullToRefresh(enableRefresh());
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

    private void initActionBar(View view) {
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        ImageView mBackBtn = (ImageView) view.findViewById(R.id.btn_back);
        ImageView mMenuBtn = (ImageView) view.findViewById(R.id.btn_menu);
        mBackBtn.setVisibility(View.GONE);
        mMenuBtn.setOnClickListener(this);
        mMenuBtn.setVisibility(isShowMenu() ? View.VISIBLE : View.GONE);
        mMenuBtn.setImageResource((getMenuResId() == -1) ? R.drawable.ic_menu : getMenuResId());

        setActionBarTitle(getActionBarTitle());
    }

    protected void setActionBarTitle(String title) {
        mTvTitle.setText(title);
    }

    protected boolean isShowMenu() {
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unSubscribe();
        mPtrFrameLayout.refreshComplete();
    }

    @Override
    public RefreshPresenter getPresenter() {
        return mPresenter;
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

    @Override
    public boolean enableRefresh() {
        return true;
    }

    @Override
    public boolean enableLoadMore() {
        return false;
    }

    public void setEnableLoadMore(boolean enable) {
        if (enableLoadMore()) {
            mAdapter.setEnableLoadMore(enable);
        } else {
            mAdapter.setEnableLoadMore(false);
        }
    }

    @Override
    public void doRefresh() {
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

    @SuppressWarnings("unchecked")
    @Override
    public void onRefreshUI(T data) {
        onRefreshComplete();

        List<T> list = (List<T>) data;
        mAdapter.setNewData(list);
        setEnableLoadMore(mPresenter.hasMore(list != null ? list.size() : 0));
    }

    @Override
    public void onRefreshFailure(int errNo) {
        onRefreshComplete();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onMoreUI(T data) {
        List<T> list = (List<T>) data;
        if (list != null) {
            mAdapter.addData(list);
            mAdapter.loadMoreComplete();
            setEnableLoadMore(mPresenter.hasMore(list.size()));
        } else {
            mAdapter.loadMoreFail();
            setEnableLoadMore(false);
        }
    }

    @Override
    public void onMoreFailure(int errNo) {
        mAdapter.loadMoreFail();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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

    protected void onRefreshComplete() {
        mPtrFrameLayout.refreshComplete();

        if (mAdapter.getItemCount() == 0) {
            if (mAdapter.getEmptyView() == null) {
                setEmptyView();
            }
            mAdapter.isUseEmpty(true);
            mAdapter.notifyDataSetChanged();
        }
    }

    protected void setEmptyView() {
        mAdapter.setEmptyView(R.layout.empty_view_list);
        mAdapter.getEmptyView().findViewById(R.id.iv_reload).setOnClickListener(this);
    }

    protected
    @DrawableRes
    int getMenuResId() {
        return -1;
    }

    protected void showMenu(View view) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        return false;
    }

}
