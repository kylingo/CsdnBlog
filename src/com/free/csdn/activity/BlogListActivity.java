package com.free.csdn.activity;

import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.free.csdn.R;
import com.free.csdn.adapter.BlogListAdapter;
import com.free.csdn.app.Constants;
import com.free.csdn.bean.BlogItem;
import com.free.csdn.bean.Blogger;
import com.free.csdn.db.BlogListDb;
import com.free.csdn.db.BlogListDbImpl;
import com.free.csdn.network.HttpAsyncTask;
import com.free.csdn.network.HttpAsyncTask.OnCompleteListener;
import com.free.csdn.util.DateUtil;
import com.free.csdn.util.JsoupUtil;
import com.free.csdn.util.NetUtil;
import com.free.csdn.util.ToastUtil;
import com.free.csdn.util.URLUtil;

/**
 * 博客列表
 * 
 * @author tangqi
 * @data 2015年7月8日下午9:20:20
 *
 */
public class BlogListActivity extends BaseActivity implements
		OnItemClickListener, OnClickListener, IXListViewRefreshListener,
		IXListViewLoadMore {

	private XListView mListView;
	private BlogListAdapter mAdapter;// 列表适配器
	private HttpAsyncTask mAsyncTask;
	private ProgressBar pbLoading;

	private TextView tvUserId;
	private String userId;
	private int page = 1;
	private Blogger blogger;
	private BlogListDb blogListDb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bloglist);

		initData();
		initView();
	}

	private void initData() {
		blogger = (Blogger) getIntent().getSerializableExtra("blogger");
		userId = blogger.getUserId();
		blogListDb = new BlogListDbImpl(this, userId);
	}

	private void initView() {
		mListView = (XListView) findViewById(R.id.listView_blog);
		pbLoading = (ProgressBar) findViewById(R.id.pb_loading);
		tvUserId = (TextView) findViewById(R.id.tv_userid);
		ImageView backBtn = (ImageView) findViewById(R.id.backBtn);
		backBtn.setOnClickListener(this);

		if (blogger != null) {
			String title = blogger.getTitle();
			if (!TextUtils.isEmpty(title)) {
				tvUserId.setText(title);
			}
		}

		initListView();
	}

	/**
	 * 初始化ListView
	 */
	private void initListView() {
		mAdapter = new BlogListAdapter(this);
		mListView.setPullRefreshEnable(this);// 设置可下拉刷新
		mListView.NotRefreshAtBegin();
		mListView.setAdapter(mAdapter);// 设置适配器
		// 设置列表项点击事件
		mListView.setOnItemClickListener(this);

		// 先预加载数据，再请求最新数据
		mHandler.sendEmptyMessage(Constants.MSG_PRELOAD_DATA);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.backBtn:
			finish();
			break;

		default:
			break;
		}
	}

	/**
	 * ListView点击事件
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		// // 获得博客列表项
		BlogItem item = (BlogItem) mAdapter.getItem(position - 1);
		Intent i = new Intent();
		i.setClass(BlogListActivity.this, BlogContentActivity.class);
		i.putExtra("blogLink", item.getLink());
		i.putExtra("title", item.getTitle());
		startActivity(i);
		// 动画过渡
		overridePendingTransition(R.anim.push_left_in, R.anim.push_no);
		Log.e("position", "" + position);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		page++;
		if (NetUtil.isNetAvailable(this)) {
			requestData(page);
		} else {
			mHandler.sendEmptyMessage(Constants.MSG_PRELOAD_DATA);
		}
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		page = 1;
		requestData(page);
	}

	private void requestData(int page) {
		if (mAsyncTask != null) {
			mAsyncTask.cancel(true);
		}

		mAsyncTask = new HttpAsyncTask(this);
		String url = URLUtil.getBlogListURL(userId, page);
		mAsyncTask.execute(url);
		mAsyncTask.setOnCompleteListener(mOnCompleteListener);
	}

	private OnCompleteListener mOnCompleteListener = new OnCompleteListener() {

		@Override
		public void onComplete(String resultString) {
			// TODO Auto-generated method stub
			// 解析html页面获取列表
			if (resultString != null) {
				List<BlogItem> list = JsoupUtil
						.getBlogItemList(0, resultString);
				if (page == 1) {
					mAdapter.setList(list);
				} else {
					mAdapter.addList(list);
				}
				mAdapter.notifyDataSetChanged();
				mListView.setPullLoadEnable(BlogListActivity.this);// 设置可上拉加载

				saveDB(list);
			} else {
				ToastUtil.showToast(BlogListActivity.this, "未能获取最新数据");
			}

			pbLoading.setVisibility(View.GONE);
			mListView.stopRefresh(DateUtil.getDate());
			mListView.stopLoadMore();
		}
	};

	/**
	 * 保存数据库
	 * 
	 * @param list
	 */
	private void saveDB(final List<BlogItem> list) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				blogListDb.saveBlogList(list);
			}
		}).start();

	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case Constants.MSG_PRELOAD_DATA:
				List<BlogItem> list = blogListDb.findBlogList(page);

				if (list != null) {
					mAdapter.setList(list);
					mAdapter.notifyDataSetChanged();
					mListView.setPullLoadEnable(BlogListActivity.this);// 设置可上拉加载
					mListView.setRefreshTime(DateUtil.getDate());
					mListView.stopLoadMore();
				} else {
					// 不请求最新数据，让用户自己刷新或者加载
					pbLoading.setVisibility(View.VISIBLE);
					requestData(page);
					mListView.disablePullLoad();
				}

				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}
	};

}
