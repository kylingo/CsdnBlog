package com.free.blog.ui.view.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.free.blog.R;
import com.free.blog.domain.util.KeyBoardUtils;

/**
 * 添加博主对话框
 */

public class BloggerAddDialog extends BaseDialog implements OnClickListener {

	private Context mContext;
	private EditText mUserIdView;
	private OnConfirmListener mOnConfirmListener;

	
	public BloggerAddDialog(Context context, OnConfirmListener onConfirmListener) {
		super(context, R.style.Theme_Light_FullScreenDialogAct);
		this.mContext = context;
		this.mOnConfirmListener = onConfirmListener;

		setContentView(R.layout.dialog_blogger_add);
		mUserIdView = (EditText) this.findViewById(R.id.et_userid);
		TextView mConfirmView = (TextView) this.findViewById(R.id.btn_confirm);
		mConfirmView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_confirm:
			mOnConfirmListener.onConfirm(String.valueOf(mUserIdView.getText()));
			KeyBoardUtils.closeKeyboard(mUserIdView, mContext);
			dismiss();
			break;

		default:
			break;
		}

	}

	/**
	 * 显示在底部
	 */
	@SuppressWarnings("unused")
	public void showDialogBottom(float dimAmount) {
		Window window = this.getWindow();
		if (window != null) {
			window.setGravity(Gravity.BOTTOM);
			WindowManager.LayoutParams lp = window.getAttributes();
			lp.dimAmount = dimAmount;
			window.setAttributes(lp);
		}
		this.show();
	}
}
