package com.free.csdn.fragment;

import java.util.HashMap;
import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;

import com.free.csdn.R;
import com.free.csdn.activity.BlogListActivity;
import com.free.csdn.adapter.BloggerListAdapter;
import com.free.csdn.bean.Blogger;
import com.free.csdn.constant.Constants;
import com.free.csdn.db.BloggerDb;
import com.free.csdn.db.BloggerManager;
import com.free.csdn.db.impl.BloggerDbImpl;
import com.free.csdn.network.HttpAsyncTask;
import com.free.csdn.network.HttpAsyncTask.OnResponseListener;
import com.free.csdn.util.DateUtil;
import com.free.csdn.util.JsoupUtil;
import com.free.csdn.util.ToastUtil;
import com.free.csdn.view.BaseDialog.OnConfirmListener;
import com.free.csdn.view.BaseDialog.OnDeleteListener;
import com.free.csdn.view.BaseDialog.OnStickListener;
import com.free.csdn.view.BloggerAddDialog;
import com.free.csdn.view.BloggerOperationDialog;
import com.free.csdn.view.LoadingDialog;

/**
 * 博主列表
 * 
 * @author tangqi
 * @data 2015年7月8日下午9:20:20
 *
 */
@SuppressLint("HandlerLeak")
public class BloggerFragment extends BaseFragment implements OnItemClickListener,
		OnItemLongClickListener, OnClickListener, IXListViewRefreshListener, IXListViewLoadMore {

	private XListView mListView;
	private List<Blogger> mBloggerList;
	private BloggerListAdapter mAdapter;
	private ProgressDialog progressdialog;

	private HashMap<String, String> bloggerItem = null;
	private BloggerDb db;
	private String newUserId = null;
	private static final int MSG_ADD_SUCCESS = 1000;
	private static final int MSG_ADD_FAILURE = 1001;
	private static final int MSG_ADD_REPEAT = 1002;
	private static final int MSG_ADD_EMPTY = 1003;
	private static final int MSG_ADD_BLOG = 1004;
	private String type = BloggerDb.TYPE_ANDROID;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_blogger_list, container, false);

		initView(view);
		return view;
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		ImageView imvAdd = (ImageView) view.findViewById(R.id.imvRight);
		imvAdd.setVisibility(View.VISIBLE);
		imvAdd.setOnClickListener(this);

		db = new BloggerDbImpl(getActivity(), type);
		new BloggerManager().init(getActivity(), db, type);
		mBloggerList = db.queryAll();

		mListView = (XListView) view.findViewById(R.id.listView);
		mAdapter = new BloggerListAdapter(getActivity(), mBloggerList);
		mListView.setPullRefreshEnable(this);// 设置可下拉刷新
		if (mBloggerList != null && mBloggerList.size() > 0) {
			mListView.setPullLoadEnable(this);// 设置可上拉加载
		}
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
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Blogger blogger = (Blogger) parent.getAdapter().getItem(position);
		Intent intent = new Intent(getActivity(), BlogListActivity.class);
		intent.putExtra("blogger", blogger);
		startActivity(intent);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		final Blogger blogger = (Blogger) parent.getAdapter().getItem(position);
		BloggerOperationDialog dialog = new BloggerOperationDialog(getActivity(), blogger);
		dialog.setOnDeleteListener(new OnDeleteListener() {

			@Override
			public void onDelete(String result) {
				// TODO Auto-generated method stub
				deleleBlogger(blogger);
			}
		});

		dialog.setOnStickListener(new OnStickListener() {

			@Override
			public void onStick(String result) {
				// TODO Auto-generated method stub
				stickBlogger(blogger);
			}
		});
		dialog.show();
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		// 增加博主
		case R.id.imvRight:
			showCenterAddDialog();
			break;

		default:
			break;
		}

	}

	/**
	 * 显示添加Dialog
	 */
	private void showCenterAddDialog() {
		BloggerAddDialog dialog = new BloggerAddDialog(getActivity(), new OnConfirmListener() {

			@Override
			public void onConfirm(String result) {
				// TODO Auto-generated method stub
				if (TextUtils.isEmpty(result)) {
					mHandler.sendEmptyMessage(MSG_ADD_EMPTY);
					return;
				}

				if (db.query(result) != null) {
					mHandler.sendEmptyMessage(MSG_ADD_REPEAT);
					return;
				}

				newUserId = result;
				progressdialog = new LoadingDialog(getActivity(), "正在添加博客...");
				progressdialog.setCancelable(false);
				progressdialog.show();
				mHandler.sendEmptyMessageDelayed(MSG_ADD_BLOG, 1000);
			}

		});

		dialog.show();
	}

	/**
	 * 请求博主数据
	 * 
	 * @param result
	 */
	private void requestData(String result) {
		HttpAsyncTask httpAsyncTask = new HttpAsyncTask(getActivity());
		httpAsyncTask.execute(Constants.CSDN_BASE_URL + result);
		httpAsyncTask.setOnCompleteListener(new OnResponseListener() {
			@Override
			public void onResponse(String resultString) {
				// TODO Auto-generated method stub
				if (TextUtils.isEmpty(resultString)) {
					mHandler.sendEmptyMessage(MSG_ADD_FAILURE);
				} else {
					bloggerItem = JsoupUtil.getBloggerItem(resultString);
					mHandler.sendEmptyMessage(MSG_ADD_SUCCESS);
				}
			}
		});
	}

	/**
	 * 增加博主
	 */
	private void addBlogger() {
		Blogger blogger = new Blogger();
		blogger.setUserId(newUserId);
		blogger.setTitle(bloggerItem.get("title"));
		blogger.setDescription(bloggerItem.get("description"));
		blogger.setImgUrl(bloggerItem.get("imgUrl"));
		blogger.setLink(Constants.CSDN_BASE_URL + newUserId);
		blogger.setType(type);
		blogger.setCategory(BloggerDb.CATEGORY_MOBILE);
		blogger.setIsTop(0);
		blogger.setIsNew(1);
		blogger.setUpdateTime(System.currentTimeMillis());
		db.insert(blogger);

		mBloggerList = db.queryAll();
		mAdapter.setList(mBloggerList);
		mAdapter.notifyDataSetChanged();
	}

	/**
	 * 置顶博主
	 * 
	 * @param blogger
	 */
	private void stickBlogger(Blogger blogger) {
		if (blogger.getIsTop() == 1) {
			blogger.setIsTop(0);
			ToastUtil.show(getActivity(), "取消置顶成功");
		} else {
			blogger.setIsTop(1);
			ToastUtil.show(getActivity(), "置顶成功");
		}

		blogger.setUpdateTime(System.currentTimeMillis());
		db.insert(blogger);

		mBloggerList = db.queryAll();
		mAdapter.setList(mBloggerList);
		mAdapter.notifyDataSetChanged();

	}

	/**
	 * 删除博主
	 * 
	 * @param blogger
	 */
	private void deleleBlogger(Blogger blogger) {
		db.delete(blogger);

		mBloggerList = db.queryAll();
		mAdapter.setList(mBloggerList);
		mAdapter.notifyDataSetChanged();

		ToastUtil.show(getActivity(), "删除成功");
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub

			if (msg.what != MSG_ADD_BLOG && progressdialog != null && progressdialog.isShowing()) {
				progressdialog.dismiss();
			}

			switch (msg.what) {
			case MSG_ADD_SUCCESS:
				ToastUtil.show(getActivity(), "博客ID添加成功");
				addBlogger();
				break;

			case MSG_ADD_FAILURE:
				ToastUtil.show(getActivity(), "博客ID不存在，添加失败");
				break;

			case MSG_ADD_EMPTY:
				ToastUtil.show(getActivity(), "博客ID为空");
				break;

			case MSG_ADD_REPEAT:
				ToastUtil.show(getActivity(), "博客ID重复添加");
				break;

			case MSG_ADD_BLOG:
				requestData(newUserId);
				break;

			default:
				break;
			}

			super.handleMessage(msg);
		}

	};

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mListView.stopLoadMore(" -- THE END --");
			}
		}, 1000);
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
