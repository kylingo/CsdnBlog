package com.free.csdn.activity;

import java.util.Collections;
import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.free.csdn.R;
import com.free.csdn.adapter.CommentAdapter;
import com.free.csdn.bean.Comment;
import com.free.csdn.bean.CommentComparator;
import com.free.csdn.config.AppConstants;
import com.free.csdn.db.BlogCommentDao;
import com.free.csdn.db.impl.BlogCommentDaoImpl;
import com.free.csdn.network.HttpAsyncTask;
import com.free.csdn.network.HttpAsyncTask.OnResponseListener;
import com.free.csdn.util.DateUtil;
import com.free.csdn.util.JsoupUtil;
import com.free.csdn.util.ToastUtil;
import com.free.csdn.util.URLUtil;

/**
 * 2014/8/13
 * 
 * 博客评论列表
 * 
 * @author wwj_748
 * 
 */
public class BlogCommentActivity extends BaseActivity implements
		IXListViewRefreshListener, IXListViewLoadMore {

	private XListView mListView;
	private CommentAdapter mAdapter;
	private ImageView reLoadImageView;
	private ImageView backBtn;
	private TextView commentTV;
	private ProgressBar pbLoading;

	private HttpAsyncTask mAsyncTask;
	private String filename;
	private int page = 1;
	private int pageSize = 20;
	private BlogCommentDao blogCommentDb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);

		initData();
		initComponent();
	}

	private void initData() {
		filename = getIntent().getExtras().getString("filename"); // 获得文件名
		mAdapter = new CommentAdapter(this);

		blogCommentDb = new BlogCommentDaoImpl(this, filename);
	}

	private void initComponent() {
		pbLoading = (ProgressBar) findViewById(R.id.pb_loading);
		reLoadImageView = (ImageView) findViewById(R.id.reLoadImage);
		reLoadImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("click");
				reLoadImageView.setVisibility(View.INVISIBLE);
				onRefresh();
			}
		});

		backBtn = (ImageView) findViewById(R.id.backBtn);
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		commentTV = (TextView) findViewById(R.id.comment);

		mListView = (XListView) findViewById(R.id.listview);
		mListView.setAdapter(mAdapter);
		mListView.NotRefreshAtBegin();
		mListView.setPullRefreshEnable(this);

		// 先预加载数据，再请求最新数据
		mHandler.sendEmptyMessage(AppConstants.MSG_PRELOAD_DATA);
	}

	@Override
	public void onLoadMore() {
		page++;
		requestData(page);
	}

	@Override
	public void onRefresh() {
		page = 1;
		requestData(page);
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.push_no, R.anim.push_right_out);
	}

	private void requestData(int page) {
		if (mAsyncTask != null) {
			mAsyncTask.cancel(true);
		}

		mAsyncTask = new HttpAsyncTask(this);
		String url = URLUtil.getCommentListURL(filename, String.valueOf(page));
		mAsyncTask.execute(url);
		mAsyncTask.setOnCompleteListener(onResponseListener);
	}

	private OnResponseListener onResponseListener = new OnResponseListener() {

		@Override
		public void onResponse(String resultString) {
			// TODO Auto-generated method stub
			// 解析html页面获取列表
			if (resultString != null) {
				List<Comment> list = JsoupUtil.getBlogCommentList(resultString,
						page, pageSize);
				CommentComparator comparator = new CommentComparator();
				Collections.sort(list, comparator);
				if (page == 1) {
					mAdapter.setList(list);
				} else {
					mAdapter.addList(list);
				}
				mAdapter.notifyDataSetChanged();
				mListView.setPullLoadEnable(BlogCommentActivity.this);// 设置可上拉加载
				commentTV.setText(list.size() + "条");
				saveDB(list);

			} else {
				ToastUtil.show(BlogCommentActivity.this, "网络已断开");
				mListView.disablePullLoad();
			}

			pbLoading.setVisibility(View.GONE);
			reLoadImageView.setVisibility(View.GONE);
			mListView.stopRefresh(DateUtil.getDate());
			mListView.stopLoadMore();
		}
	};

	/**
	 * 保存数据库
	 * 
	 * @param list
	 */
	private void saveDB(final List<Comment> list) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				blogCommentDb.insert(list);
			}
		}).start();

	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case AppConstants.MSG_PRELOAD_DATA:
				mListView.setRefreshTime(DateUtil.getDate()); // 设置刷新时间
				List<Comment> list = blogCommentDb.query(page);

				if (list != null) {
					mAdapter.setList(list);
					mAdapter.notifyDataSetChanged();
					mListView.setPullLoadEnable(BlogCommentActivity.this);// 设置可上拉加载
					mListView.setRefreshTime(DateUtil.getDate());
					commentTV.setText(list.size() + "条");
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
