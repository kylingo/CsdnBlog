package com.free.blog.library.view.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.free.blog.R;

/**
 * 加载对话框
 * 
 * @author tangqi
 * @since 2015年8月8日下午5:16:41
 */

public class LoadingDialog extends ProgressDialog {

	private String mMessage;

	// private Context mContext;

	public LoadingDialog(Context context, String message) {
		// TODO Auto-generated constructor stub
		super(context, R.style.Theme_Light_LoadingDialog);

		// this.mContext = context;
		this.mMessage = message;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.dialog_loading);
		TextView messageView = (TextView) findViewById(R.id.tv_loading_dialog);
		messageView.setText(mMessage);
	}
}
