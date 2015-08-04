package com.free.csdn.network;

import com.free.csdn.util.HttpUtil;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Http请求
 * 
 * @author Frank
 *
 */
public class HttpAsyncTask extends AsyncTask<String, Void, String> {

	public HttpAsyncTask(Context context) {
	}

	private OnCompleteListener onCompleteListener;

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

	}

	@Override
	protected String doInBackground(String... params) {
		// 获取网页html数据
		String result = HttpUtil.httpGet(params[0]);

		return result;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if (null != onCompleteListener) {
			if (null == result) {
				onCompleteListener.onComplete(null);
			} else {
				onCompleteListener.onComplete(result);
			}
		}
	}

	public interface OnCompleteListener {
		public void onComplete(String resultString);
	}

	public OnCompleteListener getOnCompleteListener() {
		return onCompleteListener;
	}

	public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
		this.onCompleteListener = onCompleteListener;
	}
}
