package com.free.blog.ui.home.mine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.free.blog.R;
import com.free.blog.data.entity.BlogItem;
import com.free.blog.library.config.Config;
import com.free.blog.library.util.DateUtils;
import com.free.blog.data.local.dao.BlogCollectDao;
import com.free.blog.data.local.dao.DaoFactory;
import com.free.blog.ui.list.BlogListAdapter;
import com.free.blog.ui.base.BaseActivity;
import com.free.blog.ui.detail.BlogContentActivity;

import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

/**
 * 博客收藏列表
 * 
 * @author tangqi
 * @since 2015年7月8日下午9:20:20
 *
 */
public class BlogCollectActivity extends BaseActivity
		implements OnItemClickListener, OnClickListener, IXListViewRefreshListener, IXListViewLoadMore {

	private XListView mListView;
	private BlogListAdapter mAdapter;
	private ImageView mReLoadImageView;
	private ProgressBar mPbLoading;

	private BlogCollectDao mBlogCollectDao;
	private int mPage = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bloglist);

		initData();
		initView();
	}

	private void initData() {
		mBlogCollectDao = DaoFactory.getInstance().getBlogCollectDao(this);
	}

	private void initView() {
		mListView = (XListView) findViewById(R.id.listView_blog);
		mPbLoading = (ProgressBar) findViewById(R.id.pb_loading);
		TextView tvTitle = (TextView) findViewById(R.id.tv_title);
		ImageView mBackBtn = (ImageView) findViewById(R.id.btn_back);
		mBackBtn.setVisibility(View.VISIBLE);
		mBackBtn.setOnClickListener(this);

		tvTitle.setText("博客收藏");
		mReLoadImageView = (ImageView) findViewById(R.id.reLoadImage);
		mReLoadImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				mReLoadImageView.setVisibility(View.INVISIBLE);
				mPbLoading.setVisibility(View.VISIBLE);

				refresh();
			}
		});

		initListView();
	}

	/**
	 * 初始化ListView
	 */
	private void initListView() {
		mAdapter = new BlogListAdapter(this);
		mListView.setPullRefreshEnable(this);
		mListView.NotRefreshAtBegin();
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);

		// 先预加载数据，再请求最新数据
		mHandler.sendEmptyMessage(Config.MSG_PRELOAD_DATA);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_back:
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
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		BlogItem item = (BlogItem) mAdapter.getItem(position - 1);
		Intent i = new Intent();
		i.setClass(BlogCollectActivity.this, BlogContentActivity.class);
		i.putExtra("blogItem", item);
		startActivity(i);

		// 动画过渡
		overridePendingTransition(R.anim.push_left_in, R.anim.push_no);
	}

	@Override
	public void onLoadMore() {
		mPage++;
		mHandler.sendEmptyMessageDelayed(Config.MSG_PRELOAD_DATA, Config.MSG_PRELOAD_DATA);
	}

	@Override
	public void onRefresh() {
		refresh();
	}

	private void refresh() {
		mPage = 1;
		mHandler.sendEmptyMessageDelayed(Config.MSG_PRELOAD_DATA, Config.MSG_PRELOAD_DATA);
	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Config.MSG_PRELOAD_DATA:
				List<BlogItem> list = mBlogCollectDao.query(mPage, mPageSize);
				if (list != null && list.size() != 0) {
					mAdapter.setList(list);
					mListView.setPullLoadEnable(BlogCollectActivity.this);// 设置可上拉加载
					mListView.setRefreshTime(DateUtils.getDate());
					mListView.stopLoadMore();
					mListView.stopRefresh(DateUtils.getDate());

					mPbLoading.setVisibility(View.GONE);
				}

				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}
	};

}
