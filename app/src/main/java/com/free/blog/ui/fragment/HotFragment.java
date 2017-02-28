package com.free.blog.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.free.blog.R;
import com.free.blog.domain.bean.Channel;
import com.free.blog.domain.config.ChannelManager;
import com.free.blog.domain.config.ExtraString;
import com.free.blog.domain.util.DateUtils;
import com.free.blog.domain.util.ToastUtil;
import com.free.blog.ui.activity.HotListActivity;
import com.free.blog.ui.adapter.ChannelListAdapter;

import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

/**
 * 热门文章
 * 
 * @author Frank
 * @since 2015年9月29日下午2:36:45
 */

public class HotFragment extends BaseFragment implements OnItemClickListener, IXListViewRefreshListener, IXListViewLoadMore {

	private View rootView;
	private XListView mListView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (rootView == null) {
			// 防止Fragment的View被多次初始化
			rootView = inflater.inflate(R.layout.fragment_channel, container, false);
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
		mTitleView.setText(R.string.hot_blog);

		// 获取频道列表去掉第1个（Android）
		ChannelManager channelManager = new ChannelManager();
		List<Channel> list = channelManager.getChannelList();
		list.remove(0);
		
		mListView = (XListView) view.findViewById(R.id.listView);
		ChannelListAdapter mAdapter = new ChannelListAdapter(getActivity(), list);
//		mListView.setPullRefreshEnable(this);// 设置可下拉刷新
//		mListView.setPullLoadEnable(this);
		mListView.NotRefreshAtBegin();
		mListView.setRefreshTime(DateUtils.getDate());
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
	}

	@Override
	public void onRefresh() {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				mListView.stopRefresh(DateUtils.getDate());
				ToastUtil.showCenter(getActivity(), getActivity().getString(R.string
						.refresh_complete));
			}
		}, 1000);
	}

	@Override
	public void onLoadMore() {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				mListView.stopLoadMore("暂无更多数据");
			}
		}, 1000);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Channel channel = (Channel) parent.getAdapter().getItem(position);

		Intent intent = new Intent(getActivity(), HotListActivity.class);
		intent.putExtra(ExtraString.CHANNEL, channel);
		startActivity(intent);
	}
}
