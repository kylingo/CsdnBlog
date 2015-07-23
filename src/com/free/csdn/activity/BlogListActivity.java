package com.free.csdn.activity;

import java.util.List;

import com.free.csdn.R;
import com.free.csdn.adapter.BlogListAdapter;
import com.free.csdn.bean.BlogItem;
import com.free.csdn.bean.Blogger;
import com.free.csdn.util.DateUtil;
import com.free.csdn.util.FileUtil;
import com.free.csdn.util.HttpAsyncTask;
import com.free.csdn.util.HttpAsyncTask.OnCompleteListener;
import com.free.csdn.util.JsoupUtil;
import com.free.csdn.util.ToastUtil;
import com.free.csdn.util.URLUtil;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

/**
 * 博客列表
 * 
 * @author tangqi
 * @data 2015年7月8日下午9:20:20
 *
 */
public class BlogListActivity extends BaseActivity
		implements OnItemClickListener, IXListViewRefreshListener, IXListViewLoadMore {

	private XListView mListView;
	private BlogListAdapter mAdapter;// 列表适配器
	private HttpAsyncTask mAsyncTask;

	private TextView tvUserId;
	private String userId;
	private int page = 1;
	private Blogger blogger;
	private DbUtils db;

	// 预加载数据
	private static final int MSG_PRELOAD_DATA = 1000;

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
		db = DbUtils.create(this, FileUtil.getExternalCacheDir(this) + "/BlogList", userId + "_blog");
	}

	private void initView() {
		mListView = (XListView) findViewById(R.id.listView_blog);
		tvUserId = (TextView) findViewById(R.id.tv_userid);

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
		mListView.setAdapter(mAdapter);// 设置适配器
		mListView.setPullRefreshEnable(this);// 设置可下拉刷新
		mListView.setPullLoadEnable(this);// 设置可上拉加载
		// 设置列表项点击事件
		mListView.setOnItemClickListener(this);

		// 先预加载数据，再请求最新数据
		mHandler.sendEmptyMessage(MSG_PRELOAD_DATA);
		mListView.startRefresh();
		mListView.setRefreshTime(DateUtil.getDate());
	}

	/**
	 * ListView点击事件
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		// // 获得博客列表项
		BlogItem item = (BlogItem) mAdapter.getItem(position - 1);
		Intent i = new Intent();
		i.setClass(BlogListActivity.this, BlogDetailActivity.class);
		i.putExtra("blogLink", item.getLink());
		startActivity(i);
		// 动画过渡
		overridePendingTransition(R.anim.push_left_in, R.anim.push_no);
		Log.e("position", "" + position);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		page++;
		requestData(page);
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
				List<BlogItem> list = JsoupUtil.getBlogItemList(0, resultString);
				if (page == 1) {
					mAdapter.setList(list);
				} else {
					mAdapter.addList(list);
				}
				mAdapter.notifyDataSetChanged();

				saveDB(list);
			} else {
				ToastUtil.showCenterToast(BlogListActivity.this, "未能获取最新数据");
			}

			mListView.stopRefresh(DateUtil.getDate());
			mListView.stopLoadMore();
		}
	};

	/**
	 * 保存数据库
	 * 
	 * @param list
	 */
	private void saveDB(List<BlogItem> list) {
		try {
			for (int i = 0; i < list.size(); i++) {
				BlogItem blogItem = list.get(i);
				BlogItem findItem = db.findFirst(Selector.from(BlogItem.class).where("link", "=", blogItem.getLink()));
				if (findItem != null) {
					db.update(blogItem, WhereBuilder.b("link", "=", blogItem.getLink()));
				} else {
					db.save(blogItem);
				}
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case MSG_PRELOAD_DATA:
				try {
					List<BlogItem> list = db
							.findAll(Selector.from(BlogItem.class).where("id", "between", new String[] { "1", "20" }));
					if (list != null) {
						mAdapter.setList(list);
						mAdapter.notifyDataSetChanged();
					}
				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}
	};
}
