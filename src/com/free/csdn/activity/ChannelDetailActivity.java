/** Copyright © 2015-2020 100msh.com All Rights Reserved */
package com.free.csdn.activity;

import java.util.ArrayList;
import java.util.List;

import com.free.csdn.R;
import com.free.csdn.adapter.BloggerListAdapter;
import com.free.csdn.base.BaseActivity;
import com.free.csdn.bean.Blogger;
import com.free.csdn.bean.Channel;
import com.free.csdn.config.ExtraString;
import com.free.csdn.task.HttpAsyncTask;
import com.free.csdn.task.OnResponseListener;
import com.free.csdn.util.DateUtil;
import com.free.csdn.util.JsoupUtil;
import com.free.csdn.util.LogUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

/**
 * 频道详情
 * 
 * @author Frank
 * @date 2015年9月18日下午5:27:58
 */

public class ChannelDetailActivity extends BaseActivity implements OnClickListener, OnItemClickListener, IXListViewRefreshListener {

	private XListView mListView;

	private List<Blogger> mBloggerList;
	private BloggerListAdapter mAdapter;
	private Channel mChannel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_channel_detail);

		initData();
		initView();
		getData();
	}

	private void initData() {
		mChannel = (Channel) getIntent().getSerializableExtra(ExtraString.CHANNEL);
	}

	private void initView() {
		// TODO Auto-generated method stub
		if (mChannel != null) {
			TextView mTitleView = (TextView) findViewById(R.id.tv_title);
			mTitleView.setText(mChannel.getChannelName());
		}
		ImageView mBackBtn = (ImageView) findViewById(R.id.btn_back);
		mBackBtn.setVisibility(View.VISIBLE);
		mBackBtn.setOnClickListener(this);

		mListView = (XListView) findViewById(R.id.listView);
		mBloggerList = new ArrayList<Blogger>();
		mAdapter = new BloggerListAdapter(this, mBloggerList);
		mListView.setPullRefreshEnable(this);
		mListView.NotRefreshAtBegin();
		mListView.setRefreshTime(DateUtil.getDate());
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
	}

	/**
	 * 获取数据
	 */
	private void getData() {
		if (mChannel != null) {
			String url = mChannel.getUrl();
			HttpAsyncTask asyncTask = new HttpAsyncTask(this);
			asyncTask.execute(url);
			asyncTask.setOnResponseListener(new OnResponseListener() {

				@Override
				public void onResponse(String resultString) {
					// TODO Auto-generated method stub
					if (resultString != null) {
						List<Blogger> bloggerList = JsoupUtil.getBloggerList(mChannel.getChannelName(), resultString);
						if (bloggerList != null) {
							LogUtil.log("bloggerList size：" + bloggerList.size());
							mAdapter.setList(bloggerList);
						}
					}
				}
			});
		}
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.btn_back:
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		Blogger blogger = (Blogger) parent.getAdapter().getItem(position);
		Intent intent = new Intent(this, BlogListActivity.class);
		LogUtil.log("blogger userId:" + blogger.getUserId());
		intent.putExtra("blogger", blogger);
		startActivity(intent);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mListView.stopRefresh(DateUtil.getDate());
			}
		}, 1000);
	}

}
