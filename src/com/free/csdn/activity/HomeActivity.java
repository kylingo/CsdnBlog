package com.free.csdn.activity;

import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.free.csdn.R;
import com.free.csdn.adapter.BloggerListAdapter;
import com.free.csdn.app.Constants;
import com.free.csdn.bean.Blogger;
import com.free.csdn.dialog.AddBloggerDialog;
import com.free.csdn.interfaces.DialogListener;
import com.free.csdn.util.BloggerDB;
import com.free.csdn.util.BloggerManager;
import com.free.csdn.util.HttpUtil;
import com.free.csdn.util.JsoupUtil;
import com.free.csdn.util.ToastUtil;

/**
 * 主页
 * 
 * @author tangqi
 * @data 2015年7月8日下午9:20:20
 *
 */
@SuppressLint("HandlerLeak")
public class HomeActivity extends BaseActivity implements OnItemClickListener,
		OnClickListener {

	private ListView listView;
	private List<Blogger> bloggerList;
	private BloggerListAdapter adapter;
	private BloggerDB bloggerDB;
	private HashMap<String, String> bloggerItem = null;
	private String newUserId = null;

	private ProgressDialog progressdialog;
	private static final int MSG_ADD_SUCCESS = 1000;
	private static final int MSG_ADD_FAILURE = 1001;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.listView);
		ImageView imvAdd = (ImageView) findViewById(R.id.imvAdd);

		bloggerDB = new BloggerDB(this);
		BloggerManager.init(this, bloggerDB);
		bloggerList = bloggerDB.SelectAllBlogger();

		adapter = new BloggerListAdapter(this, bloggerList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		imvAdd.setOnClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Blogger blogger = (Blogger) parent.getAdapter().getItem(position);

		Intent intent = new Intent(HomeActivity.this, BlogListActivity.class);
		intent.putExtra("blogger", blogger);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		// 增加博主
		case R.id.imvAdd:
			AddBloggerDialog dialog = new AddBloggerDialog(this,
					new DialogListener() {

						@Override
						public void confirm(String result) {
							// TODO Auto-generated method stub
							if (TextUtils.isEmpty(result)) {
								mHandler.sendEmptyMessage(MSG_ADD_FAILURE);
								return;
							}

							newUserId = result;
							progressdialog = ProgressDialog.show(
									HomeActivity.this, null, "正在添加博客信息，请稍候...");
							new Thread(new Runnable() {
								@Override
								public void run() {
									String htmlData = HttpUtil
											.httpGet(Constants.CSDN_BASE_URL
													+ newUserId);
									if (TextUtils.isEmpty(htmlData)) {
										mHandler.sendEmptyMessage(MSG_ADD_FAILURE);
									} else {
										bloggerItem = JsoupUtil
												.getBloggerItem(htmlData);
										mHandler.sendEmptyMessage(MSG_ADD_SUCCESS);
									}

								}
							}).start();
						}
					});
			dialog.show();
			break;

		default:
			break;
		}

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
		blogger.setType("Android");
		bloggerDB.insert(blogger);

		bloggerList = bloggerDB.SelectAllBlogger();
		adapter.setList(bloggerList);
		adapter.notifyDataSetChanged();

		if (progressdialog.isShowing()) {
			progressdialog.dismiss();
		}

		ToastUtil.showCenterToast(HomeActivity.this, "博客ID添加成功！");
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
				ToastUtil.showCenterToast(HomeActivity.this, "ID不存在，添加失败！");
				break;

			default:
				break;
			}

			super.handleMessage(msg);
		}

	};

}
