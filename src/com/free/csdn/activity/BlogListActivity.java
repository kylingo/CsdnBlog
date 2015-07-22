package com.free.csdn.activity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.free.csdn.R;
import com.free.csdn.adapter.BlogListAdapter;
import com.free.csdn.bean.BlogItem;
import com.free.csdn.bean.Blogger;
import com.free.csdn.util.HttpAsyncTask;
import com.free.csdn.util.HttpAsyncTask.OnCompleteListener;
import com.free.csdn.util.JsoupUtil;
import com.free.csdn.util.URLUtil;

/**
 * 博客列表
 * 
 * @author tangqi
 * @data 2015年7月8日下午9:20:20
 *
 */
public class BlogListActivity extends BaseActivity implements
		OnItemClickListener, IXListViewRefreshListener, IXListViewLoadMore {

	private XListView mListView;
	private TextView tvUserId;
	private BlogListAdapter adapter;// 列表适配器
	private HttpAsyncTask asyncTask;

	private String userId;
	private int page = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bloglist);

		init();
	}

	private void init() {
		mListView = (XListView) findViewById(R.id.listView_blog);
		tvUserId = (TextView) findViewById(R.id.tv_userid);

		Blogger blogger = (Blogger) getIntent().getSerializableExtra("blogger");
		if (blogger != null) {
			userId = blogger.getUserId();
			String title = blogger.getTitle();
			if (!TextUtils.isEmpty(title)) {
				tvUserId.setText(title);
			}
		}

		initComponent();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
	}

	// 初始化组件
	private void initComponent() {
		adapter = new BlogListAdapter(this);
		mListView.setAdapter(adapter);// 设置适配器
		mListView.setPullRefreshEnable(this);// 设置可下拉刷新
		mListView.setPullLoadEnable(this);// 设置可上拉加载
		// 设置列表项点击事件
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// // 获得博客列表项
				BlogItem item = (BlogItem) adapter.getItem(position - 1);
				Intent i = new Intent();
				i.setClass(BlogListActivity.this, BlogDetailActivity.class);
				i.putExtra("blogLink", item.getLink());
				startActivity(i);
				// 动画过渡
				overridePendingTransition(R.anim.push_left_in, R.anim.push_no);
				Log.e("position", "" + position);
			}
		});

		mListView.startRefresh();
		mListView.setRefreshTime(getDate());
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
		if (asyncTask != null) {
			asyncTask.cancel(true);
		}

		asyncTask = new HttpAsyncTask(this);
		String url = URLUtil.getBlogListURL(userId, page);
		asyncTask.execute(url);
		asyncTask.setOnCompleteListener(mOnCompleteListener);
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
					adapter.setList(list);
				} else {
					adapter.addList(list);
				}
				adapter.notifyDataSetChanged();
			}

			mListView.stopRefresh(getDate());
			mListView.stopLoadMore();
		}
	};

	public String getDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm",
				Locale.CHINA);
		return sdf.format(new java.util.Date());
	}

}
