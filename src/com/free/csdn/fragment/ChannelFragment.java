package com.free.csdn.fragment;

import java.util.List;

import com.free.csdn.R;
import com.free.csdn.activity.ChannelDetailActivity;
import com.free.csdn.adapter.ChannelListAdapter;
import com.free.csdn.base.BaseFragment;
import com.free.csdn.bean.Channel;
import com.free.csdn.config.ChannelManager;
import com.free.csdn.config.ExtraString;
import com.free.csdn.util.DateUtil;
import com.free.csdn.util.ToastUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView;
import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

/**
 * 频道
 * 
 * @author tangqi
 * @data 2015年8月9日上午11:07:09
 */

public class ChannelFragment extends BaseFragment
		implements OnItemClickListener, OnItemLongClickListener, IXListViewRefreshListener, IXListViewLoadMore {

	private View rootView;
	private XListView mListView;
	private ChannelListAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.fragment_channel, container, false);
		}
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}

		initView(rootView);
		return rootView;
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		TextView mTitleView = (TextView) view.findViewById(R.id.tv_title);
		mTitleView.setText(R.string.change_type);

		ChannelManager channelManager = new ChannelManager(getActivity());
		List<Channel> list = channelManager.getChannelList();
		mListView = (XListView) view.findViewById(R.id.listView);
		mAdapter = new ChannelListAdapter(getActivity(), list);
		mListView.setPullRefreshEnable(this);// 设置可下拉刷新
		mListView.setPullLoadEnable(this);
		mListView.NotRefreshAtBegin();
		mListView.setRefreshTime(DateUtil.getDate());
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
		mListView.setOnItemLongClickListener(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mListView.setRefreshTime(DateUtil.getDate());
				mListView.stopRefresh();
				ToastUtil.showCenter(getActivity(), getActivity().getString(R.string.refresh_complete));
			}
		}, 1000);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mListView.stopLoadMore("暂无更多数据");
			}
		}, 1000);
		;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Channel channel = (Channel) parent.getAdapter().getItem(position);

		Intent intent = new Intent(getActivity(), ChannelDetailActivity.class);
		intent.putExtra(ExtraString.CHANNEL, channel);
		startActivity(intent);
	}

}
