package com.free.csdn.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	
	private static Toast mToast;

	/**
	 * Toast显示消息(复用一个Toast)
	 * 
	 * @param ctx
	 * @param message
	 */

	public static final void showToast(final Context ctx,
			final String message) {
		if (mToast != null) {
			mToast.cancel();
			mToast = null;
		}
		mToast = Toast.makeText(ctx, message, Toast.LENGTH_SHORT);
		mToast.show();
	}

}
