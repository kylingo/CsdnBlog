package com.free.blog.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.free.blog.R;
import com.free.blog.domain.util.VersionUtil;

/**
 * 闪屏页面
 *
 * @author tangqi
 * @data 2015年8月15日下午10:10:51
 */
public class SplashActivity extends BaseActivity {
	private TextView mTvVersion;
	private RelativeLayout mRvSplash;
	private ImageView mIvLogo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		initView();
		startAnimation();
	}

	/**
	 * 初始化
	 */
	private void initView() {
		mIvLogo = (ImageView) findViewById(R.id.iv_logo);
		mTvVersion = (TextView) findViewById(R.id.tv_version);
		mRvSplash = (RelativeLayout) findViewById(R.id.rl_splash);

		mIvLogo.setImageResource(R.drawable.ic_launcher);
		String str = VersionUtil.getVersionName(this);
		mTvVersion.setText(getString(R.string.app_version) + str);
	}

	/**
	 * 渐变展示启动屏
	 */
	private void startAnimation() {

		Animation aa = new Animation() {
		};
		aa.setDuration(3000);
		mRvSplash.startAnimation(aa);
		aa.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationEnd(Animation arg0) {
				Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
				startActivity(intent);
				finish();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationStart(Animation animation) {

			}

		});
	}

}
