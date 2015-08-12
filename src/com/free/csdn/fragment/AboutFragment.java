package com.free.csdn.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.free.csdn.R;

/**
 *	关于
 * @author tangqi
 * @data 2015年8月12日下午11:50:29
 */

public class AboutFragment extends BaseFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_about, container, false);
		initView(view);
		return view;
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		TextView mTitleView = (TextView) view.findViewById(R.id.tvTitle);
		mTitleView.setText("关于");

		WebView mWebView = (WebView) view.findViewById(R.id.webview_about);
		mWebView.loadUrl("file:///android_asset/about/about.html");
	}
}
