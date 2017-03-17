package com.free.blog.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView;

import com.free.blog.R;
import com.free.blog.data.entity.Channel;
import com.free.blog.data.network.NetEngine;
import com.free.blog.domain.config.CategoryManager;
import com.free.blog.domain.config.ExtraString;
import com.free.blog.domain.util.DateUtils;
import com.free.blog.domain.util.JsoupUtils;
import com.free.blog.domain.util.SpfUtils;
import com.free.blog.domain.util.ToastUtil;
import com.free.blog.ui.adapter.ChannelListAdapter;
import com.free.blog.ui.view.dialog.BaseDialog;
import com.free.blog.ui.view.dialog.SelectionDialog;

import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;
import rx.Subscriber;

/**
 * 专栏
 *
 * @author tangqi
 * @since 2015年8月9日上午11:07:09
 */

public class ColumnFragment extends BaseFragment
        implements OnItemClickListener, OnItemLongClickListener, IXListViewRefreshListener,
        IXListViewLoadMore {

    private View rootView;
    private XListView mListView;
    private ChannelListAdapter mAdapter;
    private String mKeywords = "android";
    private int mPage = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_column, container, false);
            initView(rootView);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }

        return rootView;
    }

    private void initView(View view) {
        TextView mTitleView = (TextView) view.findViewById(R.id.tv_title);
        mTitleView.setText(R.string.column_default);

        mListView = (XListView) view.findViewById(R.id.listView);
        mAdapter = new ChannelListAdapter(getActivity());
        mListView.setPullRefreshEnable(this);
        mListView.NotRefreshAtBegin();
        mListView.setRefreshTime(DateUtils.getDate());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);

        getData(mPage);
    }

    private void getData(int page) {
        NetEngine.getInstance().getColumnList(mKeywords, page)
                .compose(NetEngine.<String>getErrAndIOSchedulerTransformer())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        onResponse(null);

                    }

                    @Override
                    public void onNext(String s) {
                        onResponse(s);
                    }
                });
    }

    public void onResponse(String resultString) {
        if (resultString != null) {
            List<Channel> channelList = JsoupUtils.getColumnList(resultString);
            if (mPage == 1) {
                mAdapter.setList(channelList);
                mListView.setPullLoadEnable(ColumnFragment.this);
            } else {
                mAdapter.addList(channelList);
                mListView.setPullLoadEnable(ColumnFragment.this);
            }
        }

        mListView.stopRefresh(DateUtils.getDate());
        mListView.stopLoadMore();
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        String type = (String) SpfUtils.get(getActivity(), ExtraString.BLOG_TYPE, CategoryManager
                .CategoryName.ANDROID);
        mAdapter.setCheckType(type);
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        getData(mPage);
    }

    @Override
    public void onLoadMore() {
        mPage++;
        getData(mPage);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final Channel channel = (Channel) parent.getAdapter().getItem(position);
        SelectionDialog dialog = new SelectionDialog(getActivity(), "设置【" + channel
                .getChannelName() + "】为默认频道？");
        dialog.setOnConfirmListener(new BaseDialog.OnConfirmListener() {

            @Override
            public void onConfirm(String result) {
                ToastUtil.show(getActivity(), "设置成功");
                SpfUtils.put(getActivity(), ExtraString.BLOG_TYPE, channel.getChannelName());
                refresh();
            }
        });
        dialog.show();
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ToastUtil.show(getActivity(), "即将退出，敬请期待");

//        Channel channel = (Channel) parent.getAdapter().getItem(position);
//
//        Intent intent = new Intent(getActivity(), ColumnDetailActivity.class);
//        intent.putExtra(ExtraString.COLUMN, channel);
//        startActivity(intent);
    }

}
