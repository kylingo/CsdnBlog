package com.free.csdn.view;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.free.csdn.R;

/**
 * 添加博主对话框
 */

public class AddBloggerDialog extends BaseDialog implements OnClickListener {

	private EditText et_userid;
	private TextView btn_confirm;
	private OnConfirmListener mOnConfirmListener;

	// 构造方法
	public AddBloggerDialog(Context context, OnConfirmListener onConfirmListener) {
		super(context, R.style.Theme_Light_FullScreenDialogAct);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.mOnConfirmListener = onConfirmListener;

		// 绑定Layout
		this.setContentView(R.layout.dialog_add_blogger);
		// 初始化控件
		et_userid = (EditText) this.findViewById(R.id.et_userid);
		btn_confirm = (TextView) this.findViewById(R.id.btn_confirm);

		btn_confirm.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_confirm:
			mOnConfirmListener.onConfirm(String.valueOf(et_userid.getText()));
			dismiss();
			break;

		default:
			break;
		}

	}

	/**
	 * 显示在底部
	 */
	public void showDialogBottom(float dimAmount) {
		Window window = this.getWindow();
		window.setGravity(Gravity.BOTTOM);
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.dimAmount = dimAmount;
		window.setAttributes(lp);
		this.show();
	}
}
