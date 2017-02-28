package com.free.blog.ui.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.free.blog.R;
import com.free.blog.domain.bean.Blogger;

/**
 * 博主相关操作
 * 
 * @author tangqi
 * @since 2015年8月8日下午9:54:05
 */

public class BloggerOperationDialog extends BaseDialog implements View.OnClickListener {

	private OnDeleteListener mOnDeleteListener;
	private OnStickListener mOnStickListener;
	private Blogger mBlogger;

	public BloggerOperationDialog(Context context, Blogger blogger) {
		super(context, R.style.Theme_Light_FullScreenDialogAct);

		this.mBlogger = blogger;
		setContentView(R.layout.dialog_blogger_operation);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TextView deleteView = (TextView) findViewById(R.id.tv_delete_blogger);
		TextView stickView = (TextView) findViewById(R.id.tv_stick_blogger);
		TextView cancelView = (TextView) findViewById(R.id.tv_cancel_operate);

		if (mBlogger.getIsTop() == 0) {
			stickView.setText("置顶博主");
		} else {
			stickView.setText("取消置顶");
		}

		deleteView.setOnClickListener(this);
		stickView.setOnClickListener(this);
		cancelView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		dismiss();

		switch (v.getId()) {
		case R.id.tv_delete_blogger:
			mOnDeleteListener.onDelete(null);
			break;

		case R.id.tv_stick_blogger:
			mOnStickListener.onStick(null);
			break;

		case R.id.tv_cancel_operate:
			// TODO
			break;

		default:
			break;
		}
	}

	public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
		this.mOnDeleteListener = onDeleteListener;
	}

	public void setOnStickListener(OnStickListener onStickListener) {
		this.mOnStickListener = onStickListener;
	}

}
