package com.free.blog.ui.home.hot;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.free.blog.R;
import com.free.blog.ui.base.BaseActivity;

/**
 * 关于
 * 
 * @author tangqi
 * @since 2015年8月9日下午11:09:57
 */

public class AboutActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		initView();
	}

	private void initView() {
		TextView mTitleView = (TextView) findViewById(R.id.tv_title);
		mTitleView.setText("关于");

		ImageView mBackBtn = (ImageView) findViewById(R.id.btn_back);
		mBackBtn.setVisibility(View.VISIBLE);
		mBackBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		WebView mWebView = (WebView) findViewById(R.id.webview_about);
		mWebView.loadUrl("file:///android_asset/about/about.html");
	}
}
