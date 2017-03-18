package com.free.blog.ui.home.hot;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.free.blog.R;
import com.free.blog.data.entity.BlogItem;
import com.free.blog.data.entity.Channel;
import com.free.blog.library.config.AppConstants;
import com.free.blog.library.config.CategoryManager;
import com.free.blog.library.config.ExtraString;
import com.free.blog.library.task.HttpAsyncTask;
import com.free.blog.library.task.OnResponseListener;
import com.free.blog.library.util.DateUtils;
import com.free.blog.library.util.JsoupUtils;
import com.free.blog.library.util.NetUtils;
import com.free.blog.library.util.ToastUtil;
import com.free.blog.data.local.dao.BlogItemDao;
import com.free.blog.data.local.dao.DaoFactory;
import com.free.blog.ui.list.BlogListAdapter;
import com.free.blog.ui.base.BaseActivity;
import com.free.blog.ui.detail.BlogContentActivity;

import java.util.List;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

/**
 * 热门文章列表
 * 
 * @author Frank
 * @date 2015年9月29日下午3:02:29
 */

public class HotListActivity extends BaseActivity implements OnClickListener, OnItemClickListener, IXListViewRefreshListener {

	private XListView mListView;
	private ImageView mReLoadImageView;
	private ProgressBar mPbLoading;

	private String mChannelName;
	private String mUrl;
	private BlogItemDao mBlogItemDao;
	private BlogListAdapter mAdapter;
	private List<BlogItem> mBlogList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_bloglist);
		initData();
		initView();

		queryDb();
	}

	private void initData() {
		Channel mChannel = (Channel) getIntent().getSerializableExtra(ExtraString.CHANNEL);
		if (mChannel != null) {
			mChannelName = mChannel.getChannelName();
			mUrl = mChannel.getUrl().replace("experts.html", "hot.html");
		} else {
			mChannelName = CategoryManager.CategoryName.ANDROID;
		}

		mBlogItemDao = DaoFactory.getInstance().getBlogItemDao(this, mChannelName);
	}

	private void initView() {
		ImageView mBackBtn = (ImageView) findViewById(R.id.btn_back);
		mBackBtn.setOnClickListener(this);

		TextView tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText(mChannelName);

		mPbLoading = (ProgressBar) findViewById(R.id.pb_loading);
		mReLoadImageView = (ImageView) findViewById(R.id.reLoadImage);
		mReLoadImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				mReLoadImageView.setVisibility(View.INVISIBLE);
				mPbLoading.setVisibility(View.VISIBLE);

				requestData();
			}
		});

		initListView();
	}

	private void initListView() {
		mListView = (XListView) findViewById(R.id.listView_blog);
		mAdapter = new BlogListAdapter(this);

		mListView.setPullRefreshEnable(this);
		mListView.NotRefreshAtBegin();
		mListView.setRefreshTime(DateUtils.getDate());
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		BlogItem item = (BlogItem) arg0.getAdapter().getItem(position);
		Intent i = new Intent();
		i.setClass(HotListActivity.this, BlogContentActivity.class);
		i.putExtra("blogItem", item);
		startActivity(i);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_no);
	}

	@Override
	public void onRefresh() {
		requestData();
	}

	/**
	 * 查询数据库
	 */
	private void queryDb() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				mBlogList = mBlogItemDao.queryAll();
				if (mBlogList != null && mBlogList.size() > 0) {
					// 数据库有数据，则更新UI
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							mAdapter.setList(mBlogList);
						}
					});
				} else {
					// 否则请求数据
					mPbLoading.setVisibility(View.VISIBLE);
					requestData();
				}
			}
		}).start();
	}

	/**
	 * 请求数据
	 */
	protected void requestData() {
		HttpAsyncTask mAsyncTask = new HttpAsyncTask(this);
		mAsyncTask.execute(mUrl);
		mAsyncTask.setOnResponseListener(new OnResponseListener() {

			@Override
			public void onResponse(String resultString) {
				mPbLoading.setVisibility(View.GONE);
				mReLoadImageView.setVisibility(View.GONE);
				mListView.stopRefresh(DateUtils.getDate());

				if (!TextUtils.isEmpty(resultString)) {
					mBlogList = JsoupUtils.getHotBlogList(AppConstants.BLOG_CATEGORY_ALL,
							resultString);
					mAdapter.setList(mBlogList);
					saveDb(mBlogList);
				} else {
					if (NetUtils.isNetAvailable(HotListActivity.this)) {
						ToastUtil.show(HotListActivity.this, "暂无最新数据");
					} else {
						ToastUtil.show(HotListActivity.this, "网络已断开");
						mReLoadImageView.setVisibility(View.VISIBLE);
					}
				}
			}
		});
	}

	/**
	 * 保存数据库
	 */
	private void saveDb(final List<BlogItem> blogList) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				mBlogItemDao.deleteAll();
				mBlogItemDao.insert(mChannelName, blogList);
			}
		}).start();
	}

}
