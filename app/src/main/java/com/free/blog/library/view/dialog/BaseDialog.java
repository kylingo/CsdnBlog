package com.free.blog.library.view.dialog;

import android.app.Dialog;
import android.content.Context;

/**
 *
 * @author tangqi
 * @since 2015年8月8日下午4:26:32
 */

public class BaseDialog extends Dialog {

	@SuppressWarnings("unused")
	public BaseDialog(Context context) {
		super(context);
	}

	public BaseDialog(Context context, int theme) {
		super(context, theme);
	}

	/**
	 * 监听确定
	 */
	public interface OnConfirmListener {

		void onConfirm(String result);
	}

	/**
	 * 监听取消
	 */
	public interface OnCancelListener {

		void onCancel(String result);
	}
	
	/**
	 * 监听删除
	 */
	public interface OnDeleteListener {

		void onDelete(String result);
	}

	/**
	 * 监听置顶
	 */
	public interface OnStickListener {

		void onStick(String result);
	}

}
