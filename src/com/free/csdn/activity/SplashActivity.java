package com.free.csdn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.free.csdn.R;
import com.free.csdn.base.BaseActivity;
import com.free.csdn.util.ACache;
import com.free.csdn.util.VersionUtil;

public class SplashActivity extends BaseActivity {
	private TextView mTvVersion, mTvLogoName;
	private RelativeLayout mRvSplash;
	private ACache mCache;
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
		mTvLogoName = (TextView) findViewById(R.id.tv_logo_name);
		mTvVersion = (TextView) findViewById(R.id.tv_version);
		mRvSplash = (RelativeLayout) findViewById(R.id.rl_splash);
		mCache = ACache.get(this);
		String name = mCache.getAsString("name");
		try {
			mIvLogo.setImageResource(R.drawable.ic_launcher);
			if (!name.equals("")) {
				mTvLogoName.setText(name + ",欢迎回来");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		String str = VersionUtil.getVersionName(this);
		mTvVersion.setText("当前版本：" + str);
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
				Intent intent = new Intent(SplashActivity.this, MainActivity.class);
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
