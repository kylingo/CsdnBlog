package com.free.csdn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.free.csdn.R;
import com.free.csdn.base.BaseActivity;
import com.free.csdn.util.FileUtils;
import com.free.csdn.util.VersionUtil;
import com.free.csdn.view.dialog.BaseDialog.OnConfirmListener;
import com.free.csdn.view.dialog.SelectionDialog;
import com.umeng.update.UmengUpdateAgent;

/**
 * 设置
 * 
 * @author tangqi
 * @data 2015年8月15日下午6:35:57
 */

public class SettingsActivity extends BaseActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		TextView tvTitle = (TextView) findViewById(R.id.tv_title);
		ImageView btnBack = (ImageView) findViewById(R.id.btn_back);
		TextView tvVersionName = (TextView) findViewById(R.id.tv_version_name);
		LinearLayout llCheckUpgrade = (LinearLayout) findViewById(R.id.ll_settings_check_upgrade);
		LinearLayout llClearCache = (LinearLayout) findViewById(R.id.ll_settings_clear_cache);
		LinearLayout llUserFeedback = (LinearLayout) findViewById(R.id.ll_settings_user_feedback);
		LinearLayout llUpdateLog = (LinearLayout) findViewById(R.id.ll_settings_update_log);
		LinearLayout llShareApp = (LinearLayout) findViewById(R.id.ll_settings_share_app);
		LinearLayout llSettingsAboutApp = (LinearLayout) findViewById(R.id.ll_settings_about_us);
		LinearLayout llSettingsExit = (LinearLayout) findViewById(R.id.ll_settings_exit);

		tvTitle.setText(R.string.settings);
		tvVersionName.setText(getString(R.string.setttings_now_version)
				+ VersionUtil.getVersionName(this));
		btnBack.setOnClickListener(this);
		btnBack.setVisibility(View.VISIBLE);
		llCheckUpgrade.setOnClickListener(this);
		llClearCache.setOnClickListener(this);
		llUserFeedback.setOnClickListener(this);
		llUpdateLog.setOnClickListener(this);
		llShareApp.setOnClickListener(this);
		llSettingsAboutApp.setOnClickListener(this);
		llSettingsExit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		// 返回
		case R.id.btn_back:
			finish();
			break;

		// 检查更新
		case R.id.ll_settings_check_upgrade:
			checkUpgrade();
			break;

		// 清除缓存
		case R.id.ll_settings_clear_cache:
			clearCache();
			break;

		// 用户反馈
		case R.id.ll_settings_user_feedback:
			feedback();
			break;

		// 更新日志
		case R.id.ll_settings_update_log:
			updateLog();
			break;

		// 分享好友
		case R.id.ll_settings_share_app:
			shareApp();
			break;

		// 关于我们
		case R.id.ll_settings_about_us:
			aboutUs();
			break;

		// 退出
		case R.id.ll_settings_exit:
			exit();
			break;

		default:
			break;
		}
	}

	/**
	 * 检查更新
	 */
	private void checkUpgrade() {
		// TODO Auto-generated method stub
		UmengUpdateAgent.forceUpdate(this);
	}

	/**
	 * 清除缓存
	 */
	private void clearCache() {
		// TODO Auto-generated method stub
		// FileUtils.getFileSize(f);
	}

	/**
	 * 用户反馈
	 */
	private void feedback() {
		// TODO Auto-generated method stub

	}

	/**
	 * 更新日志
	 */
	private void updateLog() {
		// TODO Auto-generated method stub

	}

	/**
	 * 分享好友
	 */
	private void shareApp() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, "CSDN博客之星，非常好用，赶紧去各大应用市场下载吧。");
		startActivity(Intent.createChooser(intent, "CSDN博客下载"));
	}

	/**
	 * 关于我们
	 */
	private void aboutUs() {
		// TODO Auto-generated method stub
		startActivity(AboutActivity.class);
	}

	/**
	 * 退出
	 */
	private void exit() {
		// TODO Auto-generated method stub
		SelectionDialog dialog = new SelectionDialog(this, getString(R.string.confirm_to_exit));
		dialog.setOnConfirmListener(new OnConfirmListener() {

			@Override
			public void onConfirm(String result) {
				// TODO Auto-generated method stub
				MyApplication.getInstance().onTerminate();
			}
		});
		dialog.show();
	}

	/**
	 * 启动Activity
	 * 
	 * @param cls
	 */
	private void startActivity(Class<?> cls) {
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}
}
