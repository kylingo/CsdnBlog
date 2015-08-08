package com.free.csdn.activity;

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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;

import com.free.csdn.R;
import com.free.csdn.adapter.BloggerListAdapter;
import com.free.csdn.bean.Blogger;
import com.free.csdn.constant.Constants;
import com.free.csdn.db.BloggerDb;
import com.free.csdn.db.BloggerManager;
import com.free.csdn.db.impl.BloggerDbImpl;
import com.free.csdn.util.DateUtil;
import com.free.csdn.util.HttpUtil;
import com.free.csdn.util.JsoupUtil;
import com.free.csdn.util.ToastUtil;
import com.free.csdn.view.AddBloggerDialog;
import com.free.csdn.view.BaseDialog.OnConfirmListener;
import com.umeng.update.UmengUpdateAgent;

/**
 * 主页
 * 
 * @author tangqi
 * @data 2015年7月8日下午9:20:20
 *
 */
@SuppressLint("HandlerLeak")
public class HomeActivity extends BaseActivity implements OnItemClickListener, OnItemLongClickListener,
		OnClickListener, IXListViewRefreshListener, IXListViewLoadMore {

	private XListView mListView;
	private List<Blogger> mBloggerList;
	private BloggerListAdapter mAdapter;
	private ProgressDialog progressdialog;

	private HashMap<String, String> bloggerItem = null;
	private BloggerDb db;
	private String newUserId = null;
	private static final int MSG_ADD_SUCCESS = 1000;
	private static final int MSG_ADD_FAILURE = 1001;
	private long exitTime;
	private String type = BloggerDb.TYPE_ANDROID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		ImageView imvAdd = (ImageView) findViewById(R.id.imvAdd);
		imvAdd.setOnClickListener(this);

		db = new BloggerDbImpl(this);
		BloggerManager.init(this, db);
		mBloggerList = db.queryAll(type);

		mListView = (XListView) findViewById(R.id.listView);
		mAdapter = new BloggerListAdapter(this, mBloggerList);
		mListView.setPullRefreshEnable(this);// 设置可下拉刷新
		if (mBloggerList != null && mBloggerList.size() > 0) {
			mListView.setPullLoadEnable(this);// 设置可上拉加载
		}
		mListView.NotRefreshAtBegin();
		mListView.setRefreshTime(DateUtil.getDate());
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
		mListView.setOnItemLongClickListener(this);

		initUmengUpdate();
	}

	/**
	 * 友盟自动更新
	 */
	private void initUmengUpdate() {
		UmengUpdateAgent.update(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Blogger blogger = (Blogger) parent.getAdapter().getItem(position);

		Intent intent = new Intent(HomeActivity.this, BlogListActivity.class);
		intent.putExtra("blogger", blogger);
		startActivity(intent);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		ToastUtil.showToast(HomeActivity.this, "onItemLongClick：" + position);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		// 增加博主
		case R.id.imvAdd:
			showAddDialog();
			break;

		default:
			break;
		}

	}

	/**
	 * 显示添加Dialog
	 */
	private void showAddDialog() {
		AddBloggerDialog dialog = new AddBloggerDialog(this, new OnConfirmListener() {

			@Override
			public void onConfirm(String result) {
				// TODO Auto-generated method stub
				if (TextUtils.isEmpty(result)) {
					mHandler.sendEmptyMessage(MSG_ADD_FAILURE);
					return;
				}

				newUserId = result;
				progressdialog = ProgressDialog.show(HomeActivity.this, null, "正在添加博客信息，请稍候...");
				new Thread(new Runnable() {
					@Override
					public void run() {
						String htmlData = HttpUtil.httpGet(Constants.CSDN_BASE_URL + newUserId);
						if (TextUtils.isEmpty(htmlData)) {
							mHandler.sendEmptyMessage(MSG_ADD_FAILURE);
						} else {
							bloggerItem = JsoupUtil.getBloggerItem(htmlData);
							mHandler.sendEmptyMessage(MSG_ADD_SUCCESS);
						}

					}
				}).start();
			}
		});

		dialog.show();
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
		blogger.setIsNew(1);
		blogger.setUpdateTime(System.currentTimeMillis());
		db.insert(blogger);

		mBloggerList = db.queryAll(type);
		mAdapter.setList(mBloggerList);
		mAdapter.notifyDataSetChanged();

		if (progressdialog.isShowing()) {
			progressdialog.dismiss();
		}

		ToastUtil.showToast(HomeActivity.this, "博客ID添加成功！");
	}

	/**
	 * 删除博主
	 * 
	 * @param blogger
	 */
	private void deleleBlogger(Blogger blogger) {
		db.delete(blogger);

		mBloggerList = db.queryAll(type);
		mAdapter.setList(mBloggerList);
		mAdapter.notifyDataSetChanged();

		ToastUtil.showToast(HomeActivity.this, "博客删除成功！");
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case MSG_ADD_SUCCESS:
				addBlogger();
				break;

			case MSG_ADD_FAILURE:
				if (progressdialog != null && progressdialog.isShowing()) {
					progressdialog.dismiss();
				}
				ToastUtil.showToast(HomeActivity.this, "ID不存在，添加失败！");
				break;

			default:
				break;
			}

			super.handleMessage(msg);
		}

	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				ToastUtil.showToast(this, "再按一次退出程序");
				exitTime = System.currentTimeMillis();
			} else {
				System.exit(0);
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

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
