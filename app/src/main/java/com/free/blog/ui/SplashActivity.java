package com.free.blog.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.free.blog.R;
import com.free.blog.library.util.VersionUtils;
import com.free.blog.ui.base.activity.BaseActivity;
import com.free.blog.ui.home.HomeActivity;

/**
 * 闪屏页面
 *
 * @author tangqi
 * @since 2015年8月15日下午10:10:51
 */
public class SplashActivity extends BaseActivity {
    private static final int DURATION_SPLASH = 3 * 1000;
    private RelativeLayout mRvSplash;

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
        ImageView mIvLogo = (ImageView) findViewById(R.id.iv_logo);
        TextView mTvVersion = (TextView) findViewById(R.id.tv_version);
        mRvSplash = (RelativeLayout) findViewById(R.id.rl_splash);

        mIvLogo.setImageResource(R.drawable.ic_launcher);
        String str = VersionUtils.getVersionName(this);
        mTvVersion.setText(String.format("%s%s", getString(R.string.app_version), str));
    }

    /**
     * 渐变展示启动屏
     */
    private void startAnimation() {

        Animation aa = new Animation() {
        };
        aa.setDuration(DURATION_SPLASH);
        mRvSplash.startAnimation(aa);
        aa.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                gotoHomeActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationStart(Animation animation) {

            }

        });
    }

    private void gotoHomeActivity() {
        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

}
