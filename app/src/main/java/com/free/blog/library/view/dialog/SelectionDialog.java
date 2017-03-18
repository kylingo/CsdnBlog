package com.free.blog.library.view.dialog;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.free.blog.R;

/**
 * 选择对话框
 * 
 */
public class SelectionDialog extends BaseDialog {

	private Button btnCancel, btnConfirm;
	private String title, message, msgConfirm;
	private OnConfirmListener mOnConfirmListener;
	private OnCancelListener mOnCancelListener;

	public SelectionDialog(Context context, String message) {
		super(context, R.style.Theme_Light_FullScreenDialogAct);
		this.message = message;
	}

	@SuppressWarnings("unused")
	public SelectionDialog(Context context, String title, String message) {
		super(context, R.style.Theme_Light_FullScreenDialogAct);
		this.title = title;
		this.message = message;
	}

	@SuppressWarnings("unused")
	public SelectionDialog(Context context, String title, String message, String msgConfirm) {
		super(context, R.style.Theme_Light_FullScreenDialogAct);
		this.title = title;
		this.message = message;
		this.msgConfirm = msgConfirm;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_selection);
		initView();
		initListener();
	}

	private void initView() {
		btnConfirm = (Button) findViewById(R.id.btn_confirm_selection);
		btnCancel = (Button) findViewById(R.id.btn_cancle);
		TextView tvTitle = (TextView) findViewById(R.id.tv_title);
		TextView tvMessage = (TextView) findViewById(R.id.tv_message);
		if (!TextUtils.isEmpty(title)) {
			tvTitle.setText(title);
		}
		if (!TextUtils.isEmpty(message)) {
			tvMessage.setText(message);
		}
		if (!TextUtils.isEmpty(msgConfirm)) {
			btnConfirm.setText(msgConfirm);
		} else {
			btnConfirm.setText("确定");
		}
	}

	private void initListener() {

		// 取消按钮
		btnCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dismiss();

				if (mOnCancelListener != null) {
					mOnCancelListener.onCancel(null);
				}
			}
		});

		// 确定按钮
		btnConfirm.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dismiss();

				if (mOnConfirmListener != null) {
					mOnConfirmListener.onConfirm(null);
				}
			}
		});
	}

	/**
	 * 外部方法，设置确定监听器
	 */
	public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
		this.mOnConfirmListener = onConfirmListener;
	}

	/**
	 * 外部方法，设置取消监听器
	 */
	public void setOnCancelListener(OnCancelListener OnCancelListener) {
		this.mOnCancelListener = OnCancelListener;
	}

	/**
	 * 外部方法，显示对话框
	 */
	@SuppressWarnings("ConstantConditions")
	@Override
	public void show() {
		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		Point size = new Point();
		wm.getDefaultDisplay().getSize(size);

		super.show();
		getWindow().setLayout(size.x * 9 / 10, LayoutParams.WRAP_CONTENT);
	}

}
